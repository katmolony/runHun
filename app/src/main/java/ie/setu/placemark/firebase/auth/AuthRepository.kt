package ie.setu.placemark.firebase.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import ie.setu.placemark.data.model.UserProfileModel
import ie.setu.placemark.firebase.services.AuthService
import ie.setu.placemark.firebase.services.FirebaseSignInResponse
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepository
@Inject constructor(private val firebaseAuth: FirebaseAuth)
    : AuthService {

    override val currentUserId: String
        get() = firebaseAuth.currentUser?.uid.orEmpty()

    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override val isUserAuthenticatedInFirebase : Boolean
        get() = firebaseAuth.currentUser != null

    override val email: String?
        get() = firebaseAuth.currentUser?.email

    override suspend fun authenticateUser(email: String, password: String): FirebaseSignInResponse {
        return try {
                val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
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
            // Assuming you are saving userProfile to Firestore or Firebase Realtime Database
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

}
