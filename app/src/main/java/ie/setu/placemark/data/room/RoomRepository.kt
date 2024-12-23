package ie.setu.placemark.data.room

import ie.setu.placemark.data.RunModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class RoomRepository @Inject
constructor(private val runDAO: RunDAO) {
    fun getAll(): Flow<List<RunModel>>
            = runDAO.getAll()

    suspend fun insert(run: RunModel)
    { runDAO.insert(run) }

    suspend fun update(run: RunModel)
    { runDAO.update(run) }

    suspend fun delete(run: RunModel)
    { runDAO.delete(run) }
}