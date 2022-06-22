package id.co.binar.secondhand.data.remote

import id.co.binar.secondhand.model.seller.category.GetCategoryResponseItem
import id.co.binar.secondhand.model.seller.product.AddProductResponse
import id.co.binar.secondhand.model.seller.product.GetProductResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface SellerApi {

    @GET("seller/category")
    suspend fun getCategory() : Response<List<GetCategoryResponseItem>>

    @Multipart
    @POST("seller/product")
    suspend fun addProduct(
        @Header("access_token") token: String,
        @PartMap field: HashMap<String, RequestBody>,
        @Part image: MultipartBody.Part
    ) : Response<AddProductResponse>

    @GET("seller/product")
    suspend fun getProduct(
        @Header("access_token") token: String
    ) : Response<GetProductResponse>
}