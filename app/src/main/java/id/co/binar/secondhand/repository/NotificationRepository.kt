package id.co.binar.secondhand.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import id.co.binar.secondhand.data.remote.NotificationApi
import id.co.binar.secondhand.data.remote.NotificationFCMApi
import id.co.binar.secondhand.model.ErrorResponse
import id.co.binar.secondhand.model.UsersFirestore
import id.co.binar.secondhand.model.buyer.order.GetOrderResponse
import id.co.binar.secondhand.model.buyer.product.GetProductResponse
import id.co.binar.secondhand.model.notification.GetNotifResponse
import id.co.binar.secondhand.model.notification.NotificationUsers
import id.co.binar.secondhand.model.notification.NotificationUsersField
import id.co.binar.secondhand.util.DataStoreManager
import id.co.binar.secondhand.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class NotificationRepository @Inject constructor(
    private val notificationApi: NotificationApi,
    private val notificationFCMApi: NotificationFCMApi,
    private val store: DataStoreManager
) {
    fun getNotif(): Flow<Resource<List<GetNotifResponse>>> = flow {
        emit(Resource.Loading())
        try {
            val response = notificationApi.getNotif(store.getTokenId())
            if (response.isSuccessful) {
                response.body()?.let { emit(Resource.Success(it.sortedByDescending { it.id })) }
            } else {
                response.errorBody()?.let {
                    val error = Gson().fromJson(it.string(), ErrorResponse::class.java)
                    throw Exception("${error.name} : ${error.message} - ${response.code()}")
                }
            }
        } catch (ex: Exception) {
            emit(Resource.Error(ex))
        }
    }

    fun updateNotif(id: Int): Flow<Resource<GetNotifResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = notificationApi.updateNotif(store.getTokenId(), id)
            if (response.isSuccessful) {
                response.body()?.let { emit(Resource.Success(it)) }
            } else {
                response.errorBody()?.let {
                    val error = Gson().fromJson(it.string(), ErrorResponse::class.java)
                    throw Exception("${error.name} : ${error.message} - ${response.code()}")
                }
            }
        } catch (ex: Exception) {
            emit(Resource.Error(ex))
        }
    }

    fun sendNotif(product: GetProductResponse?, order: GetOrderResponse?): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())
        try {
            val item = FirebaseFirestore.getInstance().collection("notification").document(product?.user?.id.toString())
                .get().await().toObject(UsersFirestore::class.java)
            val field = NotificationUsers(
                to = item?.token_notif,
                data = NotificationUsersField(
                    title = product?.name,
                    message = "Ditawar ${order?.price}"
                )
            )
            val response = notificationFCMApi.sendNotif(field)
            if (response.isSuccessful) {
                response.body()?.let { emit(Resource.Success(true)) }
            } else {
                response.errorBody()?.let {
                    val error = Gson().fromJson(it.string(), ErrorResponse::class.java)
                    throw Exception("${error.name} : ${error.message} - ${response.code()}")
                }
            }
        } catch (ex: Exception) {
            emit(Resource.Error(ex))
        }
    }
}