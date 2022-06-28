package binar.and3.kelompok1.secondhand.repository

import binar.and3.kelompok1.secondhand.data.api.buyer.BuyerAPI
import binar.and3.kelompok1.secondhand.data.api.buyer.BuyerProduct
import binar.and3.kelompok1.secondhand.data.local.buyer.BuyerDAO
import binar.and3.kelompok1.secondhand.data.local.buyer.BuyerEntity
import retrofit2.Response
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val buyerAPI: BuyerAPI,
    private val dao: BuyerDAO
) {
    suspend fun getProduct(): Response<BuyerProduct> {
        return buyerAPI.getBuyerProduct(status = "available")
    }

    suspend fun insertProduct(buyerEntity: BuyerEntity): Long {
        return dao.insertProduct(buyerEntity)
    }
}