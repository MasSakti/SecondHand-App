package id.co.binar.secondhand.data.remote

import com.squareup.okhttp.ResponseBody
import id.co.binar.secondhand.model.notification.GetNotifResponse
import id.co.binar.secondhand.model.notification.NotificationUsers
import id.co.binar.secondhand.util.Constant.BASE_URL
import id.co.binar.secondhand.util.Constant.BASE_URL_NOTIF
import retrofit2.Response
import retrofit2.http.*

interface NotificationApi {

    @GET("notification")
    suspend fun getNotif(
        @Header("access_token") token: String
    ) : Response<List<GetNotifResponse>>

    @PATCH("notification/{id}")
    suspend fun updateNotif(
        @Header("access_token") token: String,
        @Path("id") id: Int
    ) : Response<GetNotifResponse>
}