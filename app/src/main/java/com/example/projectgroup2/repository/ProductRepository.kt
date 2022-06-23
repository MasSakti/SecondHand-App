package com.example.projectgroup2.repository

import com.example.projectgroup2.data.api.auth.AuthAPI
import com.example.projectgroup2.data.api.auth.register.RegisterRequest
import com.example.projectgroup2.data.api.auth.register.RegisterResponse
import com.example.projectgroup2.data.api.main.ProductAPI
import com.example.projectgroup2.data.api.main.buyer.product.ProductResponse
import retrofit2.Response

class ProductRepository(private val api: ProductAPI) {
    suspend fun getProductBuyer(): Response<ProductResponse> {
        return api.getProductBuyer()
    }

    suspend fun getProductBuyerDetails(id: Int): Response<ProductResponse> {
        return api.getProductBuyerDetails(id = id)
    }
}