package binar.and3.kelompok1.secondhand.data.api.seller

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

    @POST("seller/product")
    suspend fun postSellerProduct(
        @Header("access_token") accessToken: String,
        @Body request: PostProductRequest
    ): Response<PostProductResponse>

    // Seller Category
    @GET("seller/category")
    suspend fun getSellerCategory(): Response<List<GetSellerCategoryResponse>>
}