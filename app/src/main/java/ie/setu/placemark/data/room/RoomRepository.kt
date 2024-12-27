package ie.setu.placemark.data.room

import ie.setu.placemark.data.model.RunModel
import ie.setu.placemark.data.model.UserProfileModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class RoomRepository @Inject constructor(
    private val runDAO: RunDAO,
    private val userProfileDAO: UserProfileDAO
) {
    // RunModel functions
    fun getAllRuns(): Flow<List<RunModel>> = runDAO.getAll()
    suspend fun insertRun(run: RunModel) = runDAO.insert(run)
    suspend fun updateRun(run: RunModel) = runDAO.update(run.id, run.message)
    suspend fun deleteRun(run: RunModel) = runDAO.delete(run)
    fun getRun(id: Int): Flow<RunModel> = runDAO.get(id)

    // UserProfileModel functions
    fun getAllProfiles(): Flow<List<UserProfileModel>> = userProfileDAO.getAllProfiles()
    suspend fun insertProfile(profile: UserProfileModel) = userProfileDAO.insertProfile(profile)
    suspend fun updateProfile(profile: UserProfileModel) {
        userProfileDAO.updateProfile(
            profile.userId,
            profile.name,
            profile.email,
            profile.preferredUnit
        )
    }
    suspend fun deleteProfile(profile: UserProfileModel) = userProfileDAO.deleteProfile(profile)
    fun getProfile(id: Int): Flow<UserProfileModel> = userProfileDAO.getProfile(id)
}
