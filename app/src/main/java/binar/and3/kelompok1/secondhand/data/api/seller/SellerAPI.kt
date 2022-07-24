package binar.and3.kelompok1.secondhand.data.api.seller

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface SellerAPI {
    @GET("seller/product")
    suspend fun getSellerProduct(
        @Header("access_token") accessToken: String
    ): Response<List<GetProductResponse>>

    @GET("seller/product/{id}")
    suspend fun getProductById(
        @Header("access_token") accessToken: String,
        @Query("id") id: Int
    ): Response<List<GetProductByIdResponse>>

    @Multipart
    @POST("seller/product")
    suspend fun postSellerProduct(
        @Header("access_token") accessToken: String,
        @Part file: MultipartBody.Part,
        @Part("name") name: RequestBody?,
        @Part("description") description: RequestBody?,
        @Part("base_price") base_price: RequestBody?,
        @Part("category_ids") categoryIds: List<Int>,
        @Part("location") location: RequestBody?
    ): Response<PostProductResponse>

    @PATCH("seller/product/{id}")
    suspend fun patchSellerProductById(
        @Header("access_token") accessToken: String,
        @Path("id") id: Int,
        @Body patchStatusRequest: PatchStatusRequest
    ): Response<PatchSellerProductById>

    // Seller Order
    @GET("seller/order")
    suspend fun getSellerOrders(
        @Header("access_token") accessToken: String,
    ) : Response<List<GetSellerOrdersResponse>>

    @GET("seller/order/{id}")
    suspend fun getSellerOderById(
        @Header("access_token") accessToken: String,
        @Path("id") id: Int
    ) : Response<GetSellerOderByIdResponse>

    @PATCH("seller/order/{id}")
    suspend fun patchSellerOderById(
        @Header("access_token") accessToken: String,
        @Path("id") id: Int,
        @Body patchStatusRequest: PatchStatusRequest
    ) : Response<PatchSellerOderByIdResponse>

    // Seller Category
    @GET("seller/category")
    suspend fun getSellerCategory(): Response<List<GetSellerCategoryResponse>>

    // Selelr Banner
    @GET("seller/banner")
    suspend fun getSellerBanner(): Response<List<GetSellerBannerResponse>>
}