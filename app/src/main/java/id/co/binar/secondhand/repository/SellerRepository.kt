package id.co.binar.secondhand.repository

import id.co.binar.secondhand.data.remote.SellerApi
import id.co.binar.secondhand.model.auth.AddAuthRequest
import id.co.binar.secondhand.model.seller.product.AddProductRequest
import id.co.binar.secondhand.util.DataStoreManager
import kotlinx.coroutines.runBlocking
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class SellerRepository @Inject constructor(
    private val sellerApi: SellerApi,
    private val store: DataStoreManager
) {
    fun store() = store

    suspend fun getCategory() = sellerApi.getCategory()

    suspend fun addProduct(field: AddProductRequest, image: MultipartBody.Part) = sellerApi.addProduct(
        store.getTokenId(),
        hashMapOf(
            "name" to field.name.toString().toRequestBody(MultipartBody.FORM),
            "description" to field.description.toString().toRequestBody(MultipartBody.FORM),
            "base_price" to field.basePrice.toString().toRequestBody(MultipartBody.FORM),
            "category_ids" to field.categoryIds.toString().toRequestBody(MultipartBody.FORM),
            "location" to field.location.toString().toRequestBody(MultipartBody.FORM)
        ),
        image = image
    )
}