package ie.setu.placemark.data.api

import ie.setu.placemark.data.RunModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface RunService {
    @GET(ServiceEndPoints.RUNS_ENDPOINT)
    suspend fun getall(): Response<List<RunModel>>

    @GET(ServiceEndPoints.RUNS_ENDPOINT + "/{id}")
    suspend fun get(@Path("id") id: String): Response<List<RunModel>>

    @DELETE(ServiceEndPoints.RUNS_ENDPOINT + "/{id}")
    suspend fun delete(@Path("id") id: String): RunWrapper

    @POST(ServiceEndPoints.RUNS_ENDPOINT)
    suspend fun post(@Body run: RunModel): RunWrapper

    @PUT(ServiceEndPoints.RUNS_ENDPOINT + "/{id}")
    suspend fun put(@Path("id") id: String,
                    @Body run: RunModel
    ): RunWrapper
}