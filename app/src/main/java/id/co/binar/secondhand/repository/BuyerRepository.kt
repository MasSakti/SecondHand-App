package id.co.binar.secondhand.repository

import androidx.room.withTransaction
import id.co.binar.secondhand.data.local.SellerDao
import id.co.binar.secondhand.data.remote.BuyerApi
import id.co.binar.secondhand.data.remote.SellerApi
import id.co.binar.secondhand.database.RoomDatabase
import id.co.binar.secondhand.model.buyer.product.GetProductResponseItem
import id.co.binar.secondhand.util.Resource
import id.co.binar.secondhand.util.castFromRemoteToLocal
import id.co.binar.secondhand.util.networkBoundResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BuyerRepository @Inject constructor(
    private val sellerApi: SellerApi,
    private val buyerApi: BuyerApi,
    private val sellerDao: SellerDao,
    private val db: RoomDatabase
) {
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

    fun getProduct(
        category: Int? = null,
        search: String? = null
    ): Flow<Resource<List<GetProductResponseItem>>> = flow {
        emit(Resource.Loading())
        try {
            val response = buyerApi.getProduct(category = category, search = search)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Resource.Success(it))
                }
            } else {
                throw Exception("Terjadi kesalahan!")
            }
        } catch (ex: Exception) {
            emit(Resource.Error(ex))
        }
    }
}