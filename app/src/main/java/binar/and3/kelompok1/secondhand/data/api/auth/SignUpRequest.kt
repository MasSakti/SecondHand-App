package binar.and3.kelompok1.secondhand.data.api.auth

import com.google.gson.annotations.SerializedName

data class SignUpRequest(
    @SerializedName("full_name"    ) var fullName    : String? = null,
    @SerializedName("email"        ) var email       : String? = null,
    @SerializedName("password"     ) var password    : String? = null,
    @SerializedName("phone_number" ) var phoneNumber : Long?   = null,
    @SerializedName("address"      ) var address     : String? = null,
    @SerializedName("city"         ) var city        : String? = null,
    @SerializedName("image_url"    ) var imageUrl    : String? = null
)
