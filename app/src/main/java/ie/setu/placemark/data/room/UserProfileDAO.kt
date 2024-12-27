package ie.setu.placemark.data.room

import androidx.room.*
import ie.setu.placemark.data.model.UserProfileModel
import kotlinx.coroutines.flow.Flow

@Dao
interface UserProfileDAO {
    @Query("SELECT * FROM userprofilemodel")
    fun getAllProfiles(): Flow<List<UserProfileModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(profile: UserProfileModel)

    @Query("UPDATE userprofilemodel SET name = :name, email = :email, preferredUnit = :preferredUnit WHERE userId = :id")
    suspend fun updateProfile(id: Int, name: String, email: String, preferredUnit: String)

    @Delete
    suspend fun deleteProfile(profile: UserProfileModel)

    @Query("SELECT * FROM userprofilemodel WHERE userId = :id")
    fun getProfile(id: Int): Flow<UserProfileModel>
}
