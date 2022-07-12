package com.example.projectgroup2.data.api.main

import com.example.projectgroup2.data.api.main.buyer.product.GetProductResponse
import com.example.projectgroup2.data.api.main.buyer.product.ProductResponse
import com.example.projectgroup2.data.api.main.buyerorder.BuyerOrderRequest
import com.example.projectgroup2.data.api.main.buyerorder.BuyerOrderResponse
import com.example.projectgroup2.data.api.main.buyerorder.GetBuyerOrderResponse
import com.example.projectgroup2.data.api.main.category.CategoryResponse
import com.example.projectgroup2.data.api.main.productdetails.GetProductByIdResponse
import com.example.projectgroup2.data.api.main.uploadproduct.UploadProductResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ProductAPI {
    //Buyer
    @GET("buyer/product")
    suspend fun getProductBuyer(
        @Query("status") status: String,
        @Query("category_id") categoryId: String,
        @Query("search") search: String
    ): Response<List<GetProductResponse>>

    @GET("buyer/product/{id}")
    suspend fun getProductById(
        @Path("id") id: Int
    ): Response<GetProductByIdResponse>

    @POST("buyer/order")
    suspend fun buyerOrder(
        @Header("access_token") token: String,
        @Body requestBuyerOrder: BuyerOrderRequest
    ): Response<BuyerOrderResponse>

    @GET("buyer/order")
    suspend fun getBuyerOrder(
        @Header("access_token") token: String
    ): Response<List<GetBuyerOrderResponse>>

    //Seller
    @GET("seller/category")
    suspend fun getCategory(): Response<List<CategoryResponse>>

    @Multipart
    @POST("seller/product")
    suspend fun uploadProduct(
        @Header("access_token") token: String,
        @Part file: MultipartBody.Part,
        @Part("name") name: RequestBody?,
        @Part("description") description: RequestBody?,
        @Part("base_price") base_price: RequestBody?,
        @Part("category_ids") categoryIds: List<Int>,
        @Part("location") location: RequestBody?,
    ): Response<UploadProductResponse>

}