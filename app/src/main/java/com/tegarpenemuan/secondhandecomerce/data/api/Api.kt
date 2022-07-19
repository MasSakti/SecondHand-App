package com.tegarpenemuan.secondhandecomerce.data.api

import com.tegarpenemuan.secondhandecomerce.data.api.BuyerOrder.UpdateStatusOrder.UpdateStatusOrderRequest
import com.tegarpenemuan.secondhandecomerce.data.api.BuyerOrder.CreateOrder.createOrderRequest
import com.tegarpenemuan.secondhandecomerce.data.api.BuyerOrder.CreateOrder.createOrderResponse
import com.tegarpenemuan.secondhandecomerce.data.api.BuyerOrder.GetDetailOrder.GetDetailOrderResponse
import com.tegarpenemuan.secondhandecomerce.data.api.BuyerOrder.UpdateStatusOrder.UpdateStatusOrderResponse
import com.tegarpenemuan.secondhandecomerce.data.api.Notification.GetDetail.GetDetailNotifResponse
import com.tegarpenemuan.secondhandecomerce.data.api.Notification.GetNotification.GetNotifResponseItem
import com.tegarpenemuan.secondhandecomerce.data.api.Notification.updateRead.UpdateReadResponse
import com.tegarpenemuan.secondhandecomerce.data.api.banner.BannerResponseItem
import com.tegarpenemuan.secondhandecomerce.data.api.category.GetCategoryResponseItem
import com.tegarpenemuan.secondhandecomerce.data.api.Product.GetProductResponse
import com.tegarpenemuan.secondhandecomerce.data.api.SellerOrder.SellerOrderResponseItem
import com.tegarpenemuan.secondhandecomerce.data.api.getProductDetails.GetProductDetailsResponse
import com.tegarpenemuan.secondhandecomerce.data.api.getProfile.GetProfileResponse
import com.tegarpenemuan.secondhandecomerce.data.api.login.LoginRequest
import com.tegarpenemuan.secondhandecomerce.data.api.login.LoginResponse
import com.tegarpenemuan.secondhandecomerce.data.api.register.response.SuccessRegisterResponse
import com.tegarpenemuan.secondhandecomerce.data.api.updateUser.UpdateUserResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface Api {

    @Multipart
    @POST("auth/register")
    suspend fun register(
        @Part("full_name") full_name: RequestBody? = null,
        @Part("email") email: RequestBody? = null,
        @Part("password") password: RequestBody? = null,
        @Part("phone_number") phone_number: RequestBody? = null,
        @Part("address") address: RequestBody? = null,
        @Part image: MultipartBody.Part? = null,
        @Part("city") city: RequestBody? = null,
    ): Response<SuccessRegisterResponse>

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @GET("auth/user")
    suspend fun getProfile(
        @Header("access_token") access_token: String
    ): Response<GetProfileResponse>

    @Multipart
    @PUT("auth/user")
    suspend fun updateUser(
        @Header("access_token") access_token: String,
        @Part("full_name") full_name: RequestBody? = null,
        @Part("email") email: RequestBody? = null,
        @Part("password") password: RequestBody? = null,
        @Part("phone_number") phone_number: RequestBody? = null,
        @Part("address") address: RequestBody? = null,
        @Part image: MultipartBody.Part? = null,
        @Part("city") city: RequestBody? = null
    ): Response<UpdateUserResponse>

    @GET("notification")
    suspend fun getNotification(
        @Header("access_token") access_token: String
    ): Response<List<GetNotifResponseItem>>

    @GET("notification/{id}")
    suspend fun getDetailNotification(
        @Path("id") id: Int,
        @Header("access_token") access_token: String
    ): Response<GetDetailNotifResponse>

    @PATCH("notification/{id}")
    suspend fun updateReadNotif(
        @Path("id") id: Int,
        @Header("access_token") access_token: String
    ): Response<UpdateReadResponse>

    @GET("buyer/product")
    suspend fun getProduct(
        @Query("status") status: String?,
        @Query("category_id") category_id: Int?,
        @Query("search") search: String?,
    ): Response<List<GetProductResponse>>

    @GET("buyer/product/{id}")
    suspend fun getProductDetails(
        @Path("id") id: Int?
    ): Response<GetProductDetailsResponse>

    @POST("buyer/order")
    suspend fun createOrder(
        @Header("access_token") access_token: String,
        @Body request: createOrderRequest
    ): Response<createOrderResponse>

    @Multipart
    @POST("seller/product")
    suspend fun addProduct(
        @Header("access_token") access_token: String,
        @Part file: MultipartBody.Part,
        @Part("name") name: RequestBody?,
        @Part("description") description: RequestBody?,
        @Part("base_price") base_price: RequestBody?,
        @Part("category_ids") categoryIds: List<Int>,
        @Part("location") location: RequestBody?,
    ):Response<GetProductResponse>

    @GET("seller/order")
    suspend fun getOrder(
        @Header("access_token") access_token: String,
        @Query("status") status: String?
    ): Response<List<SellerOrderResponseItem>>

    @GET("seller/order/{id}")
    suspend fun getDetailOrder(
        @Header("access_token") access_token: String,
        @Path("id") id: Int
    ): Response<GetDetailOrderResponse>

    @PATCH("seller/order/{id}")
    suspend fun updateStatusOrder(
        @Header("access_token") access_token: String,
        @Path("id") id: Int,
        @Body request: UpdateStatusOrderRequest
    ): Response<UpdateStatusOrderResponse>

    @GET("seller/category")
    suspend fun getCategory(): Response<List<GetCategoryResponseItem>>

    @GET("seller/product")
    suspend fun getProduct(
        @Header("access_token") access_token: String
    ): Response<List<GetProductResponse>>

    @GET("seller/banner")
    suspend fun getBanner(): Response<List<BannerResponseItem>>
}