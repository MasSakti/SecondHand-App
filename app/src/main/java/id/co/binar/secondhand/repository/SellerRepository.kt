package id.co.binar.secondhand.repository

import androidx.room.withTransaction
import id.co.binar.secondhand.data.local.SellerDao
import id.co.binar.secondhand.data.remote.SellerApi
import id.co.binar.secondhand.database.RoomDatabase
import id.co.binar.secondhand.model.seller.product.AddProductRequest
import id.co.binar.secondhand.model.seller.product.AddProductResponse
import id.co.binar.secondhand.util.DataStoreManager
import id.co.binar.secondhand.util.Resource
import id.co.binar.secondhand.util.castFromRemoteToLocal
import id.co.binar.secondhand.util.networkBoundResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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

    fun getProduct() = networkBoundResource(
        query = {
            sellerDao.getProductHome()
        },
        fetch = {
            sellerApi.getProduct(store.getTokenId())
        },
        saveFetchResult = {
            val response = it.body().castFromRemoteToLocal()
            db.withTransaction {
                sellerDao.removeProductHome()
                sellerDao.setProductHome(response)
            }
        }
    )

    fun getCategory() = networkBoundResource(
        query = {
            sellerDao.getCategoryHome()
        },
        fetch = {
            sellerApi.getCategory()
        },
        saveFetchResult = {
            val response = it.body().castFromRemoteToLocal()
            db.withTransaction {
                sellerDao.removeCategoryHome()
                sellerDao.setCategoryHome(response)
            }
        }
    )

    suspend fun addProduct(field: AddProductRequest, image: MultipartBody.Part): Flow<Resource<AddProductResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = sellerApi.addProduct(
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
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Resource.Success(it))
                }
            } else if (response.code() == 400) {
                throw Exception("Bad Request")
            } else if (response.code() == 403) {
                throw Exception("Harus masuk akun terlebih dahulu")
            } else {
                throw Exception("Terjadi kesalahan")
            }
        } catch (ex: Exception) {
            emit(Resource.Error(ex))
        }
    }
}