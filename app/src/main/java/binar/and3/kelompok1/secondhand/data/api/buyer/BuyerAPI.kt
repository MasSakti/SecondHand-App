package binar.and3.kelompok1.secondhand.data.api.buyer

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BuyerAPI {
    @GET("buyer/product")
    suspend fun getBuyerProduct(
        @Query("status") status: String? = null,
        @Query("category_id") categoryId: Int? = null,
        @Query("search") search: String? = null
    ): Response<BuyerProductResponse>
}