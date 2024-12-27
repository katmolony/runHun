package ie.setu.placemark.data.api

import ie.setu.placemark.data.model.RunModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import ie.setu.placemark.data.model.UserProfileModel
import retrofit2.http.*

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

    // UserProfileModel API endpoints
    @GET(ServiceEndPoints.USERS_ENDPOINT + "/{email}")
    suspend fun getUserProfile(
        @Path("email") email: String
    ): Response<UserProfileModel>

    @POST(ServiceEndPoints.USERS_ENDPOINT)
    suspend fun createUserProfile(
        @Body userProfile: UserProfileModel
    ): Response<UserProfileModel>

    @PUT(ServiceEndPoints.USERS_ENDPOINT + "/{email}")
    suspend fun updateUserProfile(
        @Path("email") email: String,
        @Body userProfile: UserProfileModel
    ): Response<UserProfileModel>

    @DELETE(ServiceEndPoints.USERS_ENDPOINT + "/{email}")
    suspend fun deleteUserProfile(
        @Path("email") email: String
    ): Response<Unit>
}
