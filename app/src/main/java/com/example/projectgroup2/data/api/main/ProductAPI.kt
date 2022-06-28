package com.example.projectgroup2.data.api.main

import com.example.projectgroup2.data.api.main.buyer.product.GetProductResponse
import com.example.projectgroup2.data.api.main.buyer.product.ProductResponse
import com.example.projectgroup2.data.api.main.category.CategoryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductAPI {
    //Buyer
    @GET("buyer/product")
    suspend fun getProductBuyer(
        @Query("status") status: String,
        @Query("category_id") categoryId: String,
        @Query("search") search: String
    ): Response<List<GetProductResponse>>

    @GET("buyer/product/{id}")
    suspend fun getProductBuyerDetails(@Path("id") id: Int): Response<ProductResponse>

    //Seller
    @GET("seller/category")
    suspend fun getCategory(): Response<List<CategoryResponse>>


}