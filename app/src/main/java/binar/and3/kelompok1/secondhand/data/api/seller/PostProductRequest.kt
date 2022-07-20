package binar.and3.kelompok1.secondhand.data.api.seller

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody

data class PostProductRequest(
    @SerializedName("name") val name : String? = null,
    @SerializedName("description") val description : String? = null,
    @SerializedName("base_price") val basePrice : Int? = null,
    @SerializedName("category_ids") val categoryIds: List<Int> = emptyList(),
    @SerializedName("image") val image : MultipartBody.Part? = null,
)