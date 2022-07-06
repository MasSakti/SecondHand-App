package binar.and3.kelompok1.secondhand.repository

import binar.and3.kelompok1.secondhand.data.api.buyer.BuyerAPI
import binar.and3.kelompok1.secondhand.data.api.buyer.BuyerProductResponse
import binar.and3.kelompok1.secondhand.data.api.seller.SellerAPI
import binar.and3.kelompok1.secondhand.data.api.seller.GetProductResponse
import binar.and3.kelompok1.secondhand.data.api.seller.PostProductRequest
import binar.and3.kelompok1.secondhand.data.api.seller.PostProductResponse
import binar.and3.kelompok1.secondhand.data.local.buyer.BuyerDAO
import binar.and3.kelompok1.secondhand.data.local.buyer.BuyerEntity
import retrofit2.Response
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val buyerAPI: BuyerAPI,
    private val sellerAPI: SellerAPI,
    private val dao: BuyerDAO
) {
    suspend fun getBuyerProduct(): Response<BuyerProductResponse> {
        return buyerAPI.getBuyerProduct(status = "available")
    }

    suspend fun getSellerProduct(accessToken: String): Response<List<GetProductResponse>> {
        return sellerAPI.getSellerProduct(accessToken = accessToken)
    }

    suspend fun postSellerProduct(request: PostProductRequest, accessToken: String): Response<PostProductResponse> {
        return sellerAPI.postSellerProduct(accessToken = accessToken, request = request)
    }

    // Local Database
//    suspend fun getBuyerProductFromLocal(): BuyerProduct {
//        return dao.getProduct().let {
//            BuyerProduct(
//                id = it?.id.hashCode(),
//                name = it?.name.toString(),
//                basePrice = it?.base_price.hashCode(),
//                imageUrl = it?.imageUrl.toString(),
//                imageName = it?.imageName.toString(),
//                location = it?.location.toString(),
//                userId = it?.userId.hashCode()
//            )
//        }
//    }

    suspend fun insertProductToLocal(buyerEntity: BuyerEntity): Long {
        return dao.insertProduct(buyerEntity)
    }

}