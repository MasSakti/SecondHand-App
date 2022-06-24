package id.co.binar.secondhand.repository

import androidx.room.withTransaction
import id.co.binar.secondhand.data.local.SellerDao
import id.co.binar.secondhand.data.remote.BuyerApi
import id.co.binar.secondhand.data.remote.SellerApi
import id.co.binar.secondhand.database.RoomDatabase
import id.co.binar.secondhand.util.castFromRemoteToLocal
import id.co.binar.secondhand.util.networkBoundResource
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

    suspend fun getProduct(
        category: Int?,
        search: String?
    ) = buyerApi.getProduct(
        category = category,
        search = search
    )
}