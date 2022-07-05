package id.co.binar.secondhand.repository

import com.google.gson.Gson
import id.co.binar.secondhand.data.remote.NotificationApi
import id.co.binar.secondhand.model.ErrorResponse
import id.co.binar.secondhand.model.notification.GetNotifResponse
import id.co.binar.secondhand.util.DataStoreManager
import id.co.binar.secondhand.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NotificationRepository @Inject constructor(
    private val notificationApi: NotificationApi,
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
}