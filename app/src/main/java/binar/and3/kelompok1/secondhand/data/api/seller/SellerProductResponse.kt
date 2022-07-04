package binar.and3.kelompok1.secondhand.data.api.seller

import com.google.gson.annotations.SerializedName

data class SellerProductResponse(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("base_price") val basePrice: Long? = null,
    @SerializedName("image_url") val imageUrl: String? = null,
    @SerializedName("image_name") val imageName: String? = null,
    @SerializedName("location") val location: String? = null,
    @SerializedName("user_id") val userId: Int? = null,
    @SerializedName("categories") val categories: List<Categories>
) {
    data class Categories(
        @SerializedName("id") val id: Int? = null,
        @SerializedName("name") val name: String? = null
    )
}