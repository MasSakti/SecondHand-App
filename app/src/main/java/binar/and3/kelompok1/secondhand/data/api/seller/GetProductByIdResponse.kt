package binar.and3.kelompok1.secondhand.data.api.seller

import com.google.gson.annotations.SerializedName

data class GetProductByIdResponse(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("base_price") var basePrice: Long? = null,
    @SerializedName("image_url") var imageUrl: String? = null,
    @SerializedName("image_name") var imageName: String? = null,
    @SerializedName("location") var location: String? = null,
    @SerializedName("user_id") var userId: Int? = null,
    @SerializedName("Categories") var categories: List<Categories> = listOf(),
    @SerializedName("User") var user: User? = User()
) {
    data class Categories(
        @SerializedName("id") var id: Int? = null,
        @SerializedName("name") var name: String? = null
    )

    data class User(
        @SerializedName("id") var id: Int? = null,
        @SerializedName("full_name") var fullName: String? = null,
        @SerializedName("email") var email: String? = null,
        @SerializedName("phone_number") var phoneNumber: String? = null,
        @SerializedName("address") var address: String? = null,
        @SerializedName("image_url") var imageUrl: String? = null,
        @SerializedName("city") var city: String? = null
    )
}