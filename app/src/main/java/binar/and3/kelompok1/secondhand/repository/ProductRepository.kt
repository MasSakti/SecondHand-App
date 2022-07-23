package binar.and3.kelompok1.secondhand.repository

import binar.and3.kelompok1.secondhand.data.api.buyer.BuyerAPI
import binar.and3.kelompok1.secondhand.data.api.buyer.BuyerProductResponse
import binar.and3.kelompok1.secondhand.data.api.buyer.PostBuyerBidRequest
import binar.and3.kelompok1.secondhand.data.api.buyer.PostBuyerBidResponse
import binar.and3.kelompok1.secondhand.data.api.seller.*
import binar.and3.kelompok1.secondhand.data.local.buyer.BuyerDAO
import binar.and3.kelompok1.secondhand.data.local.buyer.BuyerEntity
import okhttp3.MultipartBody
import okhttp3.RequestBody
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

    suspend fun getBuyerProductByCategory(categoryId: String, search: String): Response<List<BuyerProductResponse>> {
        return buyerAPI.getBuyerProduct(categoryId = categoryId, status = "available", search = search)
    }

    suspend fun getBuyerProductById(id: Int): Response<GetProductByIdResponse> {
        return buyerAPI.getBuyerProductById(id = id)
    }

    suspend fun postBuyerBid(accessToken: String, postBuyerBidRequest: PostBuyerBidRequest) : Response<PostBuyerBidResponse> {
        return buyerAPI.postBuyerBid(accessToken = accessToken, postBuyerBidRequest = postBuyerBidRequest)
    }

    // Seller
    suspend fun getProductById(accessToken: String, id: Int): Response<List<GetProductByIdResponse>> {
        return sellerAPI.getProductById(accessToken = accessToken, id = id)
    }

    suspend fun getSellerProduct(accessToken: String): Response<List<GetProductResponse>> {
        return sellerAPI.getSellerProduct(accessToken = accessToken)
    }

    suspend fun postSellerProduct(
        accessToken: String,
        name: RequestBody,
        description: RequestBody,
        basePrice: RequestBody,
        categoryIds: List<Int>,
        image: MultipartBody.Part,
        location: RequestBody
    ): Response<PostProductResponse> {
        return sellerAPI.postSellerProduct(
            accessToken = accessToken,
            name = name,
            description = description,
            base_price = basePrice,
            categoryIds = categoryIds,
            file = image,
            location = location
        )
    }

    // Seller Category
    suspend fun getSellerCategory(): Response<List<GetSellerCategoryResponse>> {
        return sellerAPI.getSellerCategory()
    }

    // Local
    suspend fun insertProductToLocal(buyerEntity: BuyerEntity): Long {
        return dao.insertProduct(buyerEntity)
    }

}