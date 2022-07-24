package binar.and3.kelompok1.secondhand.data.api.seller

import com.google.gson.annotations.SerializedName

data class GetSellerBannerResponse(
    @SerializedName("id"        ) var id       : Int?    = null,
    @SerializedName("name"      ) var name     : String? = null,
    @SerializedName("image_url" ) var imageUrl : String? = null
)