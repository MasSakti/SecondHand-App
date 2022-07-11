package binar.and3.kelompok1.secondhand.repository

import binar.and3.kelompok1.secondhand.data.api.buyer.BuyerAPI
import binar.and3.kelompok1.secondhand.data.api.buyer.BuyerProductResponse
import binar.and3.kelompok1.secondhand.data.api.seller.*
import binar.and3.kelompok1.secondhand.data.local.buyer.BuyerDAO
import binar.and3.kelompok1.secondhand.data.local.buyer.BuyerEntity
import retrofit2.Response
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val buyerAPI: BuyerAPI,
    private val sellerAPI: SellerAPI,
    private val dao: BuyerDAO
) {
    // Buyer
    suspend fun getBuyerProduct(): Response<List<BuyerProductResponse>> {
        return buyerAPI.getBuyerProduct(status = "available")
    }

    suspend fun getBuyerProductById(id: Int): Response<GetProductByIdResponse> {
        return buyerAPI.getBuyerProductById(id = id)
    }

    // Seller
    suspend fun getProductById(accessToken: String, id: Int): Response<List<GetProductByIdResponse>> {
        return sellerAPI.getProductById(accessToken = accessToken, id = id)
    }

    suspend fun getSellerProduct(accessToken: String): Response<List<GetProductResponse>> {
        return sellerAPI.getSellerProduct(accessToken = accessToken)
    }

    suspend fun postSellerProduct(request: PostProductRequest, accessToken: String): Response<PostProductResponse> {
        return sellerAPI.postSellerProduct(accessToken = accessToken, request = request)
    }

    suspend fun insertProductToLocal(buyerEntity: BuyerEntity): Long {
        return dao.insertProduct(buyerEntity)
    }

}