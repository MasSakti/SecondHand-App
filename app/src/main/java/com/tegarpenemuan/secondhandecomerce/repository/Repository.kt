package com.tegarpenemuan.secondhandecomerce.repository

import com.tegarpenemuan.secondhandecomerce.data.api.Api
import com.tegarpenemuan.secondhandecomerce.data.api.BuyerOrder.createOrderRequest
import com.tegarpenemuan.secondhandecomerce.data.api.BuyerOrder.createOrderResponse
import com.tegarpenemuan.secondhandecomerce.data.api.Notification.GetDetail.GetDetailNotifResponse
import com.tegarpenemuan.secondhandecomerce.data.api.Notification.GetNotification.GetNotifResponseItem
import com.tegarpenemuan.secondhandecomerce.data.api.Notification.updateRead.UpdateReadResponse
import com.tegarpenemuan.secondhandecomerce.data.api.category.GetCategoryResponseItem
import com.tegarpenemuan.secondhandecomerce.data.api.Product.GetProductResponse
import com.tegarpenemuan.secondhandecomerce.data.api.SellerOrder.SellerOrderResponseItem
import com.tegarpenemuan.secondhandecomerce.data.api.banner.BannerResponseItem
import com.tegarpenemuan.secondhandecomerce.data.api.getProductDetails.GetProductDetailsResponse
import com.tegarpenemuan.secondhandecomerce.data.api.getProfile.GetProfileResponse
import com.tegarpenemuan.secondhandecomerce.data.api.login.LoginRequest
import com.tegarpenemuan.secondhandecomerce.data.api.login.LoginResponse
import com.tegarpenemuan.secondhandecomerce.data.api.register.request.SignUpRequest
import com.tegarpenemuan.secondhandecomerce.data.api.register.response.SuccessRegisterResponse
import com.tegarpenemuan.secondhandecomerce.data.api.updateUser.UpdateUserRequest
import com.tegarpenemuan.secondhandecomerce.data.api.updateUser.UpdateUserResponse
import com.tegarpenemuan.secondhandecomerce.data.local.UserDAO
import com.tegarpenemuan.secondhandecomerce.data.local.UserEntity
import com.tegarpenemuan.secondhandecomerce.datastore.AuthDatastoreManager
import kotlinx.coroutines.flow.firstOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(
    private val authDatastore: AuthDatastoreManager,
    private val api: Api,
    private val dao: UserDAO
) {
    suspend fun clearToken() {
        updateToken("")
    }

    suspend fun clearID() {
        setId("")
    }

    suspend fun updateToken(value: String) {
        authDatastore.setToken(value)
    }

    suspend fun getToken(): String? {
        return authDatastore.getToken().firstOrNull()
    }

    suspend fun setId(value: String) {
        authDatastore.setID(value)
    }

    suspend fun getId(): String? {
        return authDatastore.getId().firstOrNull()
    }

    suspend fun register(request: SignUpRequest): Response<SuccessRegisterResponse> {
        return api.register(
            full_name = request.full_name,
            email = request.email,
            password = request.password,
            phone_number = request.phone_number,
            address = request.address,
            image = request.image,
            city = request.city
        )
    }

    suspend fun login(request: LoginRequest): Response<LoginResponse> {
        return api.login(request)
    }

    suspend fun getProfile(access_token: String): Response<GetProfileResponse> {
        return api.getProfile(access_token = access_token)
    }

    suspend fun getProduct(
        status: String?,
        category_id: Int?,
        search: String?
    ): Response<List<GetProductResponse>> {
        return api.getProduct(status, category_id, search)
    }

    suspend fun getOrder(
        access_token: String,
        status: String?
    ): Response<List<SellerOrderResponseItem>> {
        return api.getOrder(access_token, status)
    }

    suspend fun getCategory(): Response<List<GetCategoryResponseItem>> {
        return api.getCategory()
    }

    suspend fun getNotification(access_token: String): Response<List<GetNotifResponseItem>> {
        return api.getNotification(access_token)
    }

    suspend fun getDetailNotification(
        id: Int,
        access_token: String
    ): Response<GetDetailNotifResponse> {
        return api.getDetailNotification(id, access_token)
    }

    suspend fun updateReadNotif(id: Int, access_token: String): Response<UpdateReadResponse> {
        return api.updateReadNotif(id, access_token)
    }


    suspend fun createOrder(access_token: String,request: createOrderRequest): Response<createOrderResponse> {
        return api.createOrder(access_token,request)
    }

    suspend fun updateUser(
        access_token: String,
        request: UpdateUserRequest
    ): Response<UpdateUserResponse> {
        return api.updateUser(
            access_token = access_token,
            full_name = request.full_name,
            phone_number = request.phone_number,
            address = request.address,
            image = request.image,
            city = request.city
        )
    }

    suspend fun insertUser(userEntity: UserEntity): Long {
        return dao.insertUser(userEntity)
    }

    suspend fun getUser(): UserEntity {
        return dao.getUser()!!
    }

    suspend fun deleteAll(): Int {
        return dao.deleteAll()
    }

    suspend fun updateUser(
        id: String,
        full_name: String,
        phone_number: String,
        address: String,
        image_url: String,
        city: String
    ) {
        return dao.updateUser(
            id = id,
            full_name = full_name,
            phone_number = phone_number,
            address = address,
            image_url = image_url,
            city = city
        )
    }

    suspend fun getProductId(
        id: Int
    ): Response<GetProductDetailsResponse> {
        return api.getProductDetails(
            id = id
        )
    }

    suspend fun getProductSeller(access_token: String): Response<List<GetProductResponse>> {
        return api.getProduct(access_token)
    }

    suspend fun uploadProductSeller(
        token: String,
        file : MultipartBody.Part,
        name: RequestBody,
        description: RequestBody,
        base_price: RequestBody,
        categoryIds: List<Int>,
        location: RequestBody,
    ): Response<GetProductResponse> {
        return api.addProduct(token, file, name, description, base_price, categoryIds, location)
    }

    suspend fun getBanner(): Response<List<BannerResponseItem>> {
        return api.getBanner()
    }
}