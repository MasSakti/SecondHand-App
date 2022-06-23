package com.example.projectgroup2.data.api.main

import com.example.projectgroup2.data.api.main.buyer.product.ProductResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductAPI {
    @GET("buyer/product")
    suspend fun getProductBuyer(): Response<ProductResponse>

    @GET("buyer/product/{id}")
    suspend fun getProductBuyerDetails(@Path("id") id: Int): Response<ProductResponse>
}