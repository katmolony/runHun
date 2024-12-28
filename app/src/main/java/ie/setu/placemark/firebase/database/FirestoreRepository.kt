package ie.setu.placemark.firebase.database

import com.google.firebase.firestore.FirebaseFirestore
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


class FirestoreRepository
@Inject
constructor(private val auth: AuthService,
            private val firestore: FirebaseFirestore
) : FirestoreService {

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

    }

    override suspend fun update(email: String,
                                run: Run)
    {
// COULD BE A BUG: The run object is being set as the document ID
        firestore.collection(RUN_COLLECTION)
            .document(run._id)
            .set(run).await()
    }

    override suspend fun delete(email: String,
                                runId: String)
    {
        firestore.collection(RUN_COLLECTION)
            .document(runId)
            .delete().await()
    }

    // UserProfileModel-related methods

    override suspend fun getUserProfile(email: String): User? {
        return firestore.collection(USER_COLLECTION)
            .document(email)
            .get()
            .await()
            .toObject(UserProfileModel::class.java)
    }

    override suspend fun createUserProfile(user: User) {
        firestore.collection(USER_COLLECTION)
            .document(user.email)  // Assuming 'email' is unique
            .set(user)
            .await()
    }

    override suspend fun updateUserProfile(email: String, user: User) {
        firestore.collection(USER_COLLECTION)
            .document(email)
            .set(user)  // Update the entire document with the new data
            .await()
    }

    override suspend fun deleteUserProfile(email: String) {
        firestore.collection(USER_COLLECTION)
            .document(email)
            .delete()
            .await()
    }
}
