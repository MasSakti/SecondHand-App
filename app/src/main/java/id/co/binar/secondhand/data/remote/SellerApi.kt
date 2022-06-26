package id.co.binar.secondhand.data.remote

import id.co.binar.secondhand.model.seller.category.GetCategoryResponseItem
import id.co.binar.secondhand.model.seller.product.*
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
    ) : Response<List<GetProductResponseItem>>

    @GET("seller/product/{id}")
    suspend fun getProductById(
        @Header("access_token") token: String,
        @Path("id") id: Int
    ) : Response<GetProductByIdResponse>

    @DELETE("seller/product/{id}")
    suspend fun deleteProduct(
        @Header("access_token") token: String,
        @Path("id") id: Int
    ) : Response<DeleteProductByIdResponse>
}