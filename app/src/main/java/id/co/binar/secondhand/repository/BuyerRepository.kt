package id.co.binar.secondhand.repository

import androidx.room.withTransaction
import id.co.binar.secondhand.data.local.BuyerDao
import id.co.binar.secondhand.data.local.SellerDao
import id.co.binar.secondhand.data.remote.BuyerApi
import id.co.binar.secondhand.data.remote.SellerApi
import id.co.binar.secondhand.database.RoomDatabase
import id.co.binar.secondhand.util.castFromProductRemoteToProductLocal
import id.co.binar.secondhand.util.castFromRemoteToLocal
import id.co.binar.secondhand.util.networkBoundResource
import javax.inject.Inject

class BuyerRepository @Inject constructor(
    private val sellerApi: SellerApi,
    private val buyerApi: BuyerApi,
    private val buyerDao: BuyerDao,
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

    fun getProduct() = networkBoundResource(
        query = {
            buyerDao.getProductHome()
        },
        fetch = {
            buyerApi.getProduct()
        },
        saveFetchResult = {
            val response = it.body()
            val list = response.castFromProductRemoteToProductLocal()
            db.withTransaction {
                buyerDao.removeProductHome()
                buyerDao.setProductHome(
                    list
                )
            }
        }
    )
}