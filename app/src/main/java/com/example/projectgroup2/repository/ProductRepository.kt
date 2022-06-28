package com.example.projectgroup2.repository

import com.example.projectgroup2.data.api.main.ProductAPI
import com.example.projectgroup2.data.api.main.buyer.product.GetProductResponse
import com.example.projectgroup2.data.api.main.buyer.product.ProductResponse
import com.example.projectgroup2.data.api.main.category.CategoryResponse
import retrofit2.Response
import javax.inject.Inject

class ProductRepository @Inject constructor(private val api: ProductAPI) {
    suspend fun getProductBuyer(status: String, categoryId: String, search: String)
    : Response<List<GetProductResponse>> {
        return api.getProductBuyer(status = status, categoryId = categoryId, search = search)
    }

    suspend fun getCategory(): Response<List<CategoryResponse>> {
        return api.getCategory()
    }

    suspend fun getProductBuyerDetails(id: Int): Response<ProductResponse> {
        return api.getProductBuyerDetails(id = id)
    }
}