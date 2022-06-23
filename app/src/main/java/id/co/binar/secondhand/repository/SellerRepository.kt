package id.co.binar.secondhand.repository

import androidx.room.withTransaction
import id.co.binar.secondhand.data.local.SellerDao
import id.co.binar.secondhand.data.remote.SellerApi
import id.co.binar.secondhand.database.RoomDatabase
import id.co.binar.secondhand.model.seller.product.AddProductRequest
import id.co.binar.secondhand.util.DataStoreManager
import id.co.binar.secondhand.util.castFromRemoteToLocal
import id.co.binar.secondhand.util.networkBoundResource
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class SellerRepository @Inject constructor(
    private val sellerApi: SellerApi,
    private val sellerDao: SellerDao,
    private val store: DataStoreManager,
    private val db: RoomDatabase
) {
    fun store() = store

    fun getCategory() = networkBoundResource(
        query = {
            sellerDao.getCategoryHome()
        },
        fetch = {
            sellerApi.getCategory()
        },
        saveFetchResult = {
            val response = it.body()
            val list = response.castFromRemoteToLocal()
            db.withTransaction {
                sellerDao.removeCategoryHome()
                sellerDao.setCategoryHome(list)
            }
        }
    )

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