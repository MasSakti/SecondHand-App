package binar.and3.kelompok1.secondhand.data.api.seller

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SellerAPI {
    @GET("seller/product")
    suspend fun getSellerProduct(
        @Header("access_token") accessToken: String
    ): Response<List<SellerProductResponse>>
}