package binar.and3.kelompok1.secondhand.data.api.auth

import com.google.gson.annotations.SerializedName

data class SignInResponse(
    @SerializedName("name"         ) var name        : String? = null,
    @SerializedName("email"        ) var email       : String? = null,
    @SerializedName("access_token" ) var accessToken : String? = null
)
