package id.co.binar.secondhand.data.remote

import id.co.binar.secondhand.model.ErrorResponse
import id.co.binar.secondhand.model.seller.category.GetCategoryResponse
import id.co.binar.secondhand.model.seller.order.GetOrderResponse
import id.co.binar.secondhand.model.seller.product.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface SellerApi {

    @GET("seller/category")
    suspend fun getCategory() : Response<List<GetCategoryResponse>>

    @GET("seller/order")
    suspend fun getOrder(
        @Header("access_token") token: String
    ) : Response<List<GetOrderResponse>>

    @Multipart
    @POST("seller/product")
    suspend fun addProduct(
        @Header("access_token") token: String,
        @PartMap field: HashMap<String, RequestBody>,
        @Part image: MultipartBody.Part
    ) : Response<AddProductResponse>

    @Multipart
    @PUT("seller/product/{id}")
    suspend fun editProduct(
        @Header("access_token") token: String,
        @Path("id") id: Int,
        @PartMap field: HashMap<String, RequestBody>,
        @Part image: MultipartBody.Part
    ) : Response<UpdateProductByIdResponse>

    @GET("seller/product")
    suspend fun getProduct(
        @Header("access_token") token: String
    ) : Response<List<GetProductResponse>>

    @GET("seller/product/{id}")
    suspend fun getProductById(
        @Header("access_token") token: String,
        @Path("id") id: Int
    ) : Response<GetProductResponse>

    @DELETE("seller/product/{id}")
    suspend fun deleteProduct(
        @Header("access_token") token: String,
        @Path("id") id: Int
    ) : Response<ErrorResponse>
}