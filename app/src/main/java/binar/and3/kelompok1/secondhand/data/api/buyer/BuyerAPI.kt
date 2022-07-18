package binar.and3.kelompok1.secondhand.data.api.buyer

import binar.and3.kelompok1.secondhand.data.api.seller.GetProductByIdResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BuyerAPI {
    @GET("buyer/product")
    suspend fun getBuyerProduct(
        @Query("status") status: String? = null,
        @Query("category_id") categoryId: Int? = null,
        @Query("search") search: String? = null
    ): Response<List<BuyerProductResponse>>

    @GET("buyer/product/{id}")
    suspend fun getBuyerProductById(
        @Path("id") id: Int
    ): Response<GetProductByIdResponse>
}