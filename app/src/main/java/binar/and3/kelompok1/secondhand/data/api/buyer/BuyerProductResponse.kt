package binar.and3.kelompok1.secondhand.data.api.buyer

import com.google.gson.annotations.SerializedName

data class BuyerProductResponse(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("base_price") var basePrice: Int? = null,
    @SerializedName("image_url") var imageUrl: String? = null,
    @SerializedName("image_name") var imageName: String? = null,
    @SerializedName("location") var location: String? = null,
    @SerializedName("user_id") var userId: Int? = null,
    @SerializedName("Categories") var categories: List<Categories>
) {
    data class Categories(
        @SerializedName("id") var id: Int? = null,
        @SerializedName("name") var name: String? = null
    )
}