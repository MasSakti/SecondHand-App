package id.co.binar.secondhand.repository

import androidx.room.withTransaction
import id.co.binar.secondhand.data.local.BuyerDao
import id.co.binar.secondhand.data.local.model.BuyerCategoryLocal
import id.co.binar.secondhand.data.local.model.BuyerProductLocal
import id.co.binar.secondhand.data.remote.BuyerApi
import id.co.binar.secondhand.data.remote.SellerApi
import id.co.binar.secondhand.database.RoomDatabase
import id.co.binar.secondhand.util.networkBoundResource
import javax.inject.Inject

class BuyerRepository @Inject constructor(
    private val sellerApi: SellerApi,
    private val buyerApi: BuyerApi,
    private val buyerDao: BuyerDao,
    private val db: RoomDatabase
) {
    fun getCategory() = networkBoundResource(
        query = {
            buyerDao.getCategoryHome()
        },
        fetch = {
            sellerApi.getCategory()
        },
        saveFetchResult = {
            val response = it.body()
            val list = response as List<BuyerCategoryLocal>
            db.withTransaction {
                buyerDao.removeCategoryHome()
                buyerDao.setCategoryHome(list)
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
            val list = response as List<BuyerProductLocal>
            db.withTransaction {
                buyerDao.removeProductHome()
                buyerDao.setProductHome(
                    list
                )
            }
        }
    )
}