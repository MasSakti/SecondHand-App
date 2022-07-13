package com.tegarpenemuan.secondhandecomerce.data.api.Product

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import retrofit2.http.Part
import java.io.File

class ProductRequest(
    @Part("name") val name: String? = null,
    @Part("description") val description: String? = null,
    @Part("base_price") val basePrice: Int? = null,
    @Part("category_ids") val categoryIds: ArrayList<Int> = arrayListOf(),
    @Part("location") val location: String? = null,
    @Part("image") val image: File? = null,
)