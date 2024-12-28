package ie.setu.placemark.firebase.services

import ie.setu.placemark.data.model.RunModel
import ie.setu.placemark.data.model.UserProfileModel
import kotlinx.coroutines.flow.Flow

typealias Run = RunModel
typealias Runs = Flow<List<Run>>

typealias User = UserProfileModel
typealias Users = Flow<List<User>>

interface FirestoreService {

    suspend fun getAll(email: String): Runs
    suspend fun get(email: String, runId: String): Run?
    suspend fun insert(email: String, run: Run)
    suspend fun update(email: String, run: Run)
    suspend fun delete(email: String, runId: String)

    suspend fun getUserProfile(email: String): User?
    suspend fun createUserProfile(user: User)
    suspend fun updateUserProfile(email: String, user: User)
    suspend fun deleteUserProfile(email: String)
}