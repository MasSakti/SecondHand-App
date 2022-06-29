package com.tegarpenemuan.secondhandecomerce.data.api

import com.tegarpenemuan.secondhandecomerce.data.api.category.GetCategoryResponseItem
import com.tegarpenemuan.secondhandecomerce.data.api.getCity.getCityResponse
import com.tegarpenemuan.secondhandecomerce.data.api.getNotifications.GetNotifResponseItem
import com.tegarpenemuan.secondhandecomerce.data.api.getProduct.GetProductResponse
import com.tegarpenemuan.secondhandecomerce.data.api.getProductDetails.GetProductDetailsResponse
import com.tegarpenemuan.secondhandecomerce.data.api.getProfile.GetProfileResponse
import com.tegarpenemuan.secondhandecomerce.data.api.getProvince.getProvinveResponse
import com.tegarpenemuan.secondhandecomerce.data.api.login.LoginRequest
import com.tegarpenemuan.secondhandecomerce.data.api.login.LoginResponse
import com.tegarpenemuan.secondhandecomerce.data.api.register.response.SuccessRegisterResponse
import com.tegarpenemuan.secondhandecomerce.data.api.updateUser.UpdateUserResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface AuthApi {

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

    @GET("buyer/product")
    suspend fun getProduct(
        @Query("status") status: String?,
        @Query("category_id") category_id: Int?,
        @Query("search") search: String?,
    ): Response<List<GetProductResponse>>

    @GET("seller/category")
    suspend fun getCategory(): Response<List<GetCategoryResponseItem>>

    @GET("notification")
    suspend fun getNotification(
        @Header("access_token") access_token: String
    ): Response<List<GetNotifResponseItem>>

//    @GET("notification/{id}")
//    suspend fun getNotificationById(
//        @Header("access_token") access_token: String,
//        @Path("id") id: Int
//    ): Response<List<GetNotifByIdResponseItem>>

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

    @GET("buyer/product/{id}")
    suspend fun getProductDetails(
        @Path("id") id: Int?,
//        @Field("name") name: String?,
//        @Field("category_id") category_id: Int?,
//        @Field("base_price") base_price: Int?,
//        @Field("image_url") image_url: String?,
//        @Field("location") location: String?,
//        @Field("description") description: String?,
    ): Response<GetProductDetailsResponse>

    @GET("https://dev.farizdotid.com/api/daerahindonesia/provinsi")
    suspend fun  getProvince(): Response<getProvinveResponse>

    @GET("https://dev.farizdotid.com/api/daerahindonesia/kota")
    suspend fun getCity(
        @Query ("id_provinsi") id_provinsi: Int
    ): Response <getCityResponse>

}