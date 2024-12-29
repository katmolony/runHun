package ie.setu.placemark.firebase.database

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.dataObjects
import ie.setu.placemark.data.model.UserProfileModel
import ie.setu.placemark.firebase.rules.Constants.RUN_COLLECTION
import ie.setu.placemark.firebase.rules.Constants.USER_COLLECTION
import ie.setu.placemark.firebase.rules.Constants.USER_EMAIL
import ie.setu.placemark.firebase.services.AuthService
import ie.setu.placemark.firebase.services.FirestoreService
import ie.setu.placemark.firebase.services.Run
import ie.setu.placemark.firebase.services.Runs
import ie.setu.placemark.firebase.services.User
import kotlinx.coroutines.tasks.await
import java.util.Date
import javax.inject.Inject
import com.google.firebase.firestore.toObject
import timber.log.Timber

interface AuthHelper {
    fun getCurrentUserId(): String?
    // Add any other methods FirestoreRepository needs
}

// Modify AuthRepository to implement AuthHelper
class AuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthService, AuthHelper {
    override fun getCurrentUserId(): String? {
        return firebaseAuth.currentUser?.uid
    }
    // Other methods
}

// Modify FirestoreRepository to use AuthHelper
class FirestoreRepository @Inject constructor(
    private val authHelper: AuthHelper,
    private val firestore: FirebaseFirestore,
    private val context: Context
) : FirestoreService {
    fun getUserData() {
        val userId = authHelper.getCurrentUserId()
        // Use userId to fetch data
    }

    override suspend fun getAll(email: String): Runs {

        return firestore.collection(RUN_COLLECTION)
            .whereEqualTo(USER_EMAIL, email)
            .dataObjects()
    }

    override suspend fun get(email: String,
                             runId: String): Run? {
        return firestore.collection(RUN_COLLECTION)
            .document(runId).get().await().toObject()
    }

    override suspend fun insert(email: String,
                                run: Run)
    {
        val runWithEmail = run.copy(email = email)

        firestore.collection(RUN_COLLECTION)
            .add(runWithEmail)
            .await()
//        update user stats
        calculateAndUpdateUserStats(email)

    }

    override suspend fun update(email: String,
                                run: Run)
    {
// COULD BE A BUG: The run object is being set as the document ID
        firestore.collection(RUN_COLLECTION)
            .document(run._id)
            .set(run).await()

        //        update user stats
        calculateAndUpdateUserStats(email)
    }

    override suspend fun delete(email: String,
                                runId: String)
    {
        firestore.collection(RUN_COLLECTION)
            .document(runId)
            .delete().await()

        //        update user stats
        calculateAndUpdateUserStats(email)
    }

    override suspend fun getLongestRun(email: String): Run? {
        return try {
            val querySnapshot = FirebaseFirestore.getInstance()
                .collection(RUN_COLLECTION)
                .whereEqualTo("email", email)
                .orderBy("distanceAmount", Query.Direction.DESCENDING)
                .limit(1)
                .get()
                .await()

            querySnapshot.documents.firstOrNull()?.toObject(Run::class.java)
        } catch (e: Exception) {
            Timber.e("Error fetching longest run: ${e.message}")
            null
        }
    }

    override suspend fun getMostRecentRun(email: String): Run? {
        return try {
            val querySnapshot = FirebaseFirestore.getInstance()
                .collection(RUN_COLLECTION)
                .whereEqualTo("email", email)
                .orderBy("dateRan", Query.Direction.DESCENDING)
                .limit(1)
                .get()
                .await()

            querySnapshot.documents.firstOrNull()?.toObject(Run::class.java)
        } catch (e: Exception) {
            Timber.e("Error fetching most recent run: ${e.message}")
            null
        }
    }

    // UserProfileModel-related methods

    override suspend fun getUserProfile(email: String): User? {
        val userId = getUserIdByEmail(email)
        return if (userId != null) {
            Timber.i("Fetched user ID: $userId")
            val userProfile = firestore.collection(USER_COLLECTION)
                .document(userId).get().await().toObject<User>()
            Timber.i("Fetched user profile: $userProfile")
            userProfile
        } else {
            Timber.e("User ID not found for email: $email")
            null
        }
    }

    override suspend fun createUserProfile(user: User) {
        val documentReference = firestore.collection(USER_COLLECTION)
            .add(user)
            .await()
        val userId = documentReference.id
        Timber.i("Stored user ID: $userId") // Log the user ID
        storeUserId(userId) // Store the user ID in SharedPreferences
    }

    override suspend fun updateUserProfile(email: String, user: User) {
        val userId = getUserIdByEmail(email)
        if (userId != null) {
            Timber.i("Fetched user ID: $userId")
            firestore.collection(USER_COLLECTION)
                .document(userId)
                .set(user)
                .await()
            Timber.i("Updated user profile for user ID: $userId")
        } else {
            Timber.e("User ID not found for email: $email")
        }
    }

    override suspend fun deleteUserProfile(email: String) {
        val userId = getUserIdByEmail(email)
        if (userId != null) {
            Timber.i("Fetched user ID: $userId")
            firestore.collection(USER_COLLECTION)
                .document(userId)
                .delete()
                .await()
            Timber.i("Deleted user profile for user ID: $userId")
        } else {
            Timber.e("User ID not found for email: $email")
        }
    }

    private fun storeUserId(userId: String?) {
        // Example using SharedPreferences
        val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("user_id", userId).apply()
    }
    suspend fun getUserIdByEmail(email: String): String? {
        val querySnapshot = firestore.collection(USER_COLLECTION)
            .whereEqualTo("email", email)
            .get()
            .await()
        val document = querySnapshot.documents.firstOrNull()
        val userId = document?.id
        Timber.i("Fetched user ID: $userId")
        storeUserId(userId)
        return userId
    }

    suspend fun calculateAndUpdateUserStats(email: String) {
        val firestore = FirebaseFirestore.getInstance()

        val runsQuery = firestore.collection(RUN_COLLECTION)
            .whereEqualTo("email", email)
            .get()
            .await()

        val runs = runsQuery.documents.mapNotNull { it.toObject(Run::class.java) }

        // Calculate the statistics for the user
        val totalDistanceRun = runs.sumOf { it.distanceAmount.toDouble() }
        val totalRuns = runs.size
        val averagePace = if (totalRuns > 0) totalDistanceRun / totalRuns else 0.0

        val userProfileQuery = firestore.collection(USER_COLLECTION)
            .whereEqualTo("email", email)
            .get()
            .await()
        val userProfileRef = userProfileQuery.documents.firstOrNull()?.reference

        if (userProfileRef != null) {
            firestore.runTransaction { transaction ->
                transaction.update(userProfileRef, mapOf(
                    "totalDistanceRun" to totalDistanceRun,
                    "totalRuns" to totalRuns,
                    "averagePace" to averagePace
                ))
            }.await()

            Timber.i("User stats updated successfully for $email")
        } else {
            throw IllegalStateException("User profile not found for email: $email")
        }
    }

    suspend fun userExists(userId: String): Boolean {
        return try {
            val document = firestore.collection(USER_COLLECTION).document(userId).get().await()
            document.exists()
        } catch (e: Exception) {
            Timber.e("Error checking if user exists: ${e.message}")
            false
        }
    }

    suspend fun createGoogleUserProfile(user: User): Boolean {
        return try {
            val documentReference = firestore.collection(USER_COLLECTION)
                .add(user)
                .await()
            val userId = documentReference.id
            Timber.i("Stored Google user ID: $userId") // Log the user ID
            storeUserId(userId) // Store the user ID in SharedPreferences
            true
        } catch (e: Exception) {
            Timber.e("Error creating Google user profile: ${e.message}")
            false
        }
    }

}

