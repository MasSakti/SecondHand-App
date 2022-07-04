package binar.and3.kelompok1.secondhand.repository

import binar.and3.kelompok1.secondhand.Constant
import binar.and3.kelompok1.secondhand.data.api.buyer.BuyerAPI
import binar.and3.kelompok1.secondhand.data.api.buyer.BuyerProduct
import binar.and3.kelompok1.secondhand.data.api.seller.SellerAPI
import binar.and3.kelompok1.secondhand.data.api.seller.SellerProductResponse
import binar.and3.kelompok1.secondhand.data.local.buyer.BuyerDAO
import binar.and3.kelompok1.secondhand.data.local.buyer.BuyerEntity
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Named

class ProductRepository @Inject constructor(
    private val buyerAPI: BuyerAPI,
    private val sellerAPI: SellerAPI,
    private val dao: BuyerDAO
) {
    suspend fun getBuyerProduct(): Response<BuyerProduct> {
        return buyerAPI.getBuyerProduct(status = "available")
    }

    suspend fun getSellerProduct(accessToken: String): Response<List<SellerProductResponse>> {
        return sellerAPI.getSellerProduct(accessToken = accessToken)
    }

    suspend fun insertProduct(buyerEntity: BuyerEntity): Long {
        return dao.insertProduct(buyerEntity)
    }
}