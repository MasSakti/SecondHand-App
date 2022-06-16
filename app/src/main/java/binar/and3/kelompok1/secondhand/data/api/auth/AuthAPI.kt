package binar.and3.kelompok1.secondhand.data.api.auth

import retrofit2.Response
import retrofit2.http.*

interface AuthAPI {
    @POST("auth/login")
    suspend fun signIn(@Body request: SignInRequest): Response<SignInResponse>

    @POST("auth/register")
    suspend fun signUp(@Body request: SignUpRequest): Response<SignUpResponse>

    @GET("auth/user/{id}")
    suspend fun getUser(
        @Header("access_token") token: String,
        @Path("id") id: String
    ): Response<GetUserResponse>

    @PUT("auth/user/{id}")
    suspend fun updateUser(
        @Header("access_token") token: String,
        @Path("id") id: String
    ): Response<UpdateUserDataResponse>
}