package com.example.projectgroup2.repository

import com.example.projectgroup2.data.api.main.ProductAPI
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
import javax.inject.Inject

class ProductRepository @Inject constructor(private val api: ProductAPI) {
    suspend fun getProductBuyer(status: String, categoryId: String, search: String)
    : Response<List<GetProductResponse>> {
        return api.getProductBuyer(status = status, categoryId = categoryId, search = search)
    }

    suspend fun getCategory(): Response<List<CategoryResponse>> {
        return api.getCategory()
    }

    suspend fun uploadProductSeller(
        token: String,
        file : MultipartBody.Part,
        name: RequestBody,
        description: RequestBody,
        base_price: RequestBody,
        categoryIds: List<Int>,
        location: RequestBody,
    ): Response<UploadProductResponse> {
        return api.uploadProduct(token, file, name, description, base_price, categoryIds, location)
    }

    suspend fun getProductById(id: Int): Response<GetProductByIdResponse> {
        return api.getProductById(id = id)
    }

    suspend fun buyerOrder(token: String, requestBuyerOrder: BuyerOrderRequest): Response<BuyerOrderResponse> {
        return api.buyerOrder(token = token, requestBuyerOrder = requestBuyerOrder)
    }

    suspend fun getBuyerOrder(token: String): Response<List<GetBuyerOrderResponse>> {
        return api.getBuyerOrder(token = token)
    }
}