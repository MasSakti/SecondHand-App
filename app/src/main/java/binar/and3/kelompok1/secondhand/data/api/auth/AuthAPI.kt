package binar.and3.kelompok1.secondhand.data.api.auth

import retrofit2.Response
import retrofit2.http.*

interface AuthAPI {
    @POST("auth/login")
    suspend fun signIn(@Body request: SignInRequest): Response<SignInResponse>

    @POST("auth/register")
    suspend fun signUp(@Body request: SignUpRequest): Response<SignUpResponse>

    @GET("auth/user")
    suspend fun getUser(
        @Header("access_token") token: String,
    ): Response<GetUserResponse>

    @PUT("auth/user")
    suspend fun updateUser(
        @Header("access_token") token: String,
    ): Response<UpdateUserDataResponse>
}