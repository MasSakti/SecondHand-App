package binar.and3.kelompok1.secondhand.data.api.seller

import com.google.gson.annotations.SerializedName

data class GetProductResponse(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("base_price") var basePrice: Long? = null,
    @SerializedName("image_url") var imageUrl: String? = null,
    @SerializedName("image_name") var imageName: String? = null,
    @SerializedName("location") var location: String? = null,
    @SerializedName("user_id") var userId: Int? = null,
    @SerializedName("categories") var categories: List<Categories> = emptyList()
) {
    data class Categories(
        @SerializedName("id") var id: Int? = null,
        @SerializedName("name") var name: String? = null
    )
}