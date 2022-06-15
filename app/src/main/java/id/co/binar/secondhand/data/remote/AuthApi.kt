package id.co.binar.secondhand.data.remote

import id.co.binar.secondhand.model.auth.GetAuthRequest
import id.co.binar.secondhand.model.auth.GetAuthResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/login")
    suspend fun login(
        @Body field: GetAuthRequest
    ) : Response<GetAuthResponse>
}