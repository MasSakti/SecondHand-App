package binar.and3.kelompok1.secondhand.data.api.auth

import com.google.gson.annotations.SerializedName

data class SignInRequest(
    @SerializedName("email"    ) var email    : String? = null,
    @SerializedName("password" ) var password : String? = null
)
