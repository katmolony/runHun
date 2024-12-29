package ie.setu.placemark.firebase.auth

import android.content.Context
import android.net.Uri
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import ie.setu.placemark.data.model.UserProfileModel
import ie.setu.placemark.firebase.database.FirestoreRepository
import ie.setu.placemark.firebase.services.AuthService
import ie.setu.placemark.firebase.services.FirebaseSignInResponse
import ie.setu.placemark.firebase.services.FirestoreService
import ie.setu.placemark.firebase.services.SignInWithGoogleResponse
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject

class AuthRepository
@Inject constructor(
    private val firebaseAuth: FirebaseAuth,
//    private val context: Context
)
    : AuthService {

    override val currentUserId: String
        get() = firebaseAuth.currentUser?.uid.orEmpty()

    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override val isUserAuthenticatedInFirebase : Boolean
        get() = firebaseAuth.currentUser != null

    override val email: String?
        get() = firebaseAuth.currentUser?.email

    override val customPhotoUri: Uri?
        get() = firebaseAuth.currentUser!!.photoUrl

    override suspend fun authenticateUser(email: String, password: String): FirebaseSignInResponse {
        return try {
                val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
//                val userId = result.user?.uid
//                // Store the userId in a session or state
//                storeUserId(userId)
                Response.Success(result.user!!)
        } catch (e: Exception) {
                e.printStackTrace()
                Response.Failure(e)
        }
    }

    override suspend fun createUser(name: String, email: String, password: String): FirebaseSignInResponse {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            result.user?.updateProfile(UserProfileChangeRequest.Builder().setDisplayName(name).build())?.await()
//            val userId = result.user?.uid
            // Store the userId in a session or state
//            storeUserId(userId)
            return Response.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Response.Failure(e)
        }
    }

    override suspend fun signOut() {
        firebaseAuth.signOut()
    }

    // Implement createUserProfile
    override suspend fun createUserProfile(userProfile: UserProfileModel): Response<FirebaseUser> {
        return try {
            val user = firebaseAuth.currentUser
            user?.let {
                // Perform the profile creation logic (e.g., saving to database)
                // Return the FirebaseUser
                Response.Success(it)
            } ?: Response.Failure(Exception("User not authenticated"))
        } catch (e: Exception) {
            e.printStackTrace()
            Response.Failure(e)
        }
    }
    override suspend fun authenticateGoogleUser(googleIdToken: String) : FirebaseSignInResponse {
        return try {
            val firebaseCredential = GoogleAuthProvider.getCredential(googleIdToken, null)
            val result = firebaseAuth.signInWithCredential(firebaseCredential).await()
            Response.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Response.Failure(e)
        }
    }

    override suspend fun firebaseSignInWithGoogle(
        googleCredential: AuthCredential
    ): FirebaseSignInResponse {
        return try {
            val authResult = firebaseAuth.signInWithCredential(googleCredential).await()
            val user = authResult.user
            if (user != null) {
                val isNewUser = authResult.additionalUserInfo?.isNewUser ?: false
                if (isNewUser) {
//                    addUserToFirestore()
                }
                Response.Success(user)
            } else {
                Response.Failure(Exception("User is null"))
            }
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

//    suspend fun addUserToFirestore(): Response<Boolean> {
//        val firebaseUser = firebaseAuth.currentUser
//        return if (firebaseUser != null) {
//            try {
//                val userProfile = UserProfileModel(
//                    userId = 0,
//                    name = firebaseUser.displayName ?: "",
//                    email = firebaseUser.email ?: "",
//                    profilePictureUrl = "",
//                    totalDistanceRun = 0.0,
//                    totalRuns = 0,
//                    averagePace = 0.0,
//                    preferredUnit = "km" // Default if null
//                )
//
//                val result = FirestoreService.createUserProfile(userProfile)
//                Timber.i("RegisterViewModel, User profile creation result: $result") // Log the result
//                Response.Success(true)
//            } catch (e: Exception) {
//                Response.Failure(e)
//            }
//        } else {
//            Response.Failure(Exception("No authenticated user available"))
//        }
//    }

    override suspend fun updatePhoto(uri: Uri) : FirebaseSignInResponse {
        return try {
            currentUser!!.updateProfile(UserProfileChangeRequest
                .Builder()
                .setPhotoUri(uri)
                .build()).await()
            return Response.Success(currentUser!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Response.Failure(e)
        }
    }
}
