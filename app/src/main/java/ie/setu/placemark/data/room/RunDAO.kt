package ie.setu.placemark.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ie.setu.placemark.data.model.RunModel
import kotlinx.coroutines.flow.Flow

@Dao
interface RunDAO {
    @Query("SELECT * FROM runmodel")
    fun getAll(): Flow<List<RunModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(run: RunModel)

    @Query("UPDATE runmodel SET message=:message WHERE id = :id")
    suspend fun update(id: Int, message:String)

    @Delete
    suspend fun delete(run: RunModel)

    @Query("SELECT * FROM runmodel WHERE id=:id")
    fun get(id: Int): Flow<RunModel>

}