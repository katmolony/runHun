package ie.setu.placemark.data.api

import ie.setu.placemark.data.model.RunModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface RunService {

    // Existing calls
    @GET(ServiceEndPoints.RUNS_ENDPOINT + "/{email}")
    suspend fun getall(
        @Path("email") email: String
    ): Response<List<RunModel>>

    @GET(ServiceEndPoints.RUNS_ENDPOINT + "/{email}/{id}")
    suspend fun get(
        @Path("email") email: String,
        @Path("id") id: String
    ): Response<List<RunModel>>

    @DELETE(ServiceEndPoints.RUNS_ENDPOINT + "/{email}/{id}")
    suspend fun delete(
        @Path("email") email: String,
        @Path("id") id: String
    ): RunWrapper

    @POST(ServiceEndPoints.RUNS_ENDPOINT + "/{email}")
    suspend fun post(
        @Path("email") email: String,
        @Body run: RunModel
    ): RunWrapper

    @PUT(ServiceEndPoints.RUNS_ENDPOINT + "/{email}/{id}")
    suspend fun put(
        @Path("email") email: String,
        @Path("id") id: String,
        @Body run: RunModel
    ): RunWrapper

    // New call to get all runs regardless of email
    @GET(ServiceEndPoints.RUNS_ENDPOINT)
    suspend fun getAllRuns(): Response<List<RunModel>>
}
