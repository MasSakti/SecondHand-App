package id.co.binar.secondhand.data.remote

import id.co.binar.secondhand.model.notification.GetNotifResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface NotificationApi {

    @GET("notification")
    suspend fun getNotif(
        @Header("access_token") token: String
    ) : Response<List<GetNotifResponse>>
}