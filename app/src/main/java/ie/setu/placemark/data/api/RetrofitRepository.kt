package ie.setu.placemark.data.api

import ie.setu.placemark.data.model.RunModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RetrofitRepository @Inject
constructor(private val serviceApi: RunService)  {

    suspend fun getAll(): List<RunModel>
    {
        return withContext(Dispatchers.IO) {
            val runs = serviceApi.getall()
            runs.body() ?: emptyList()
        }
    }

    suspend fun get(id: String): List<RunModel>
    {
        return withContext(Dispatchers.IO) {
            val run = serviceApi.get(id)
            run.body() ?: emptyList()
        }
    }

    suspend fun insert(run: RunModel) : RunWrapper
    {
        return withContext(Dispatchers.IO) {
            val wrapper = serviceApi.post(run)
            wrapper
        }
    }

    suspend fun update(run: RunModel) : RunWrapper
    {
        return withContext(Dispatchers.IO) {
            val wrapper = serviceApi.put(run._id,run)
            wrapper
        }
    }

    suspend fun delete(run: RunModel) : RunWrapper
    {
        return withContext(Dispatchers.IO) {
            val wrapper = serviceApi.delete(run._id)
            wrapper
        }
    }
}
