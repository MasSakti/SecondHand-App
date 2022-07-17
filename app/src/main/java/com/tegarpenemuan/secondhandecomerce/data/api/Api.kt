package com.tegarpenemuan.secondhandecomerce.data.api

import com.tegarpenemuan.secondhandecomerce.data.api.banner.BannerResponseItem
import com.tegarpenemuan.secondhandecomerce.data.api.category.GetCategoryResponseItem
import com.tegarpenemuan.secondhandecomerce.data.api.getCity.getCityResponse
import com.tegarpenemuan.secondhandecomerce.data.api.getNotification.GetNotifResponseItem
import com.tegarpenemuan.secondhandecomerce.data.api.Product.GetProductResponse
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

    @GET("buyer/product/{id}")
    suspend fun getProductDetails(
        @Path("id") id: Int?
    ): Response<GetProductDetailsResponse>

    @GET("buyer/product")
    suspend fun getProduct(
        @Query("status") status: String?,
        @Query("category_id") category_id: Int?,
        @Query("search") search: String?,
    ): Response<List<GetProductResponse>>

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

    @GET("seller/category")
    suspend fun getCategory(): Response<List<GetCategoryResponseItem>>

    @GET("seller/product")
    suspend fun getProduct(
        @Header("access_token") access_token: String
    ): Response<List<GetProductResponse>>

    @GET("seller/banner")
    suspend fun getBanner(): Response<List<BannerResponseItem>>
}