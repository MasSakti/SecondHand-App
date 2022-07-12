package binar.and3.kelompok1.secondhand.data.api.seller

import com.google.gson.annotations.SerializedName

data class GetSellerCategory(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null
)
