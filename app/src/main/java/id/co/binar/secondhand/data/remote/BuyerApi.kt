package id.co.binar.secondhand.data.remote

import id.co.binar.secondhand.model.buyer.product.GetProductByIdResponse
import id.co.binar.secondhand.model.buyer.product.GetProductResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BuyerApi {

    @GET("buyer/product")
    suspend fun getProduct(
        @Query("status") status: String? = null,
        @Query("category_id") category: Int? = null,
        @Query("search") search: String? = null
    ) : Response<List<GetProductResponse>>

    @GET("buyer/product/{id}")
    suspend fun getProductById(
        @Path("id") id: Int
    ) : Response<GetProductByIdResponse>
}