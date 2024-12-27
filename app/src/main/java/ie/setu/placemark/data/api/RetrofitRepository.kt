package ie.setu.placemark.data.api

import ie.setu.placemark.data.model.RunModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import javax.inject.Inject

class RetrofitRepository @Inject
constructor(private val serviceApi: RunService)  {

    suspend fun getAll(email: String): List<RunModel>
    {
        return withContext(Dispatchers.IO) {
            val runs = serviceApi.getall(email)
            runs.body() ?: emptyList()
        }
    }

    suspend fun get(email: String, id: String): List<RunModel>
    {
        return withContext(Dispatchers.IO) {
            val run = serviceApi.get(email,id)
            run.body() ?: emptyList()
        }
    }

    suspend fun insert(email: String, run: RunModel): RunWrapper {
        // Ensure the run's email is set to the provided email before sending the request
        val updatedRun = run.copy(email = email)
        return withContext(Dispatchers.IO) {
            val wrapper = serviceApi.post(email, updatedRun)
            wrapper
        }
    }

    suspend fun update(email: String, run: RunModel) : RunWrapper
    {
        return withContext(Dispatchers.IO) {
            val wrapper = serviceApi.put(email, run._id, run)
            wrapper
        }
    }

    suspend fun delete(email: String, run: RunModel) : RunWrapper
    {
        return withContext(Dispatchers.IO) {
            val wrapper = serviceApi.delete(email, run._id)
            wrapper
        }
    }
    // New function to fetch all runs, regardless of email
    suspend fun getAllRuns(): List<RunModel> {
        return withContext(Dispatchers.IO) {
            val runs = serviceApi.getAllRuns()
            runs.body() ?: emptyList()
        }
    }
}
