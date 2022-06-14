package id.co.binar.secondhand.model.auth

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AddAuthRequest(

    @field:SerializedName("password")
    val password: String? = null,

    @field:SerializedName("full_name")
    val fullName: String? = null,

    @field:SerializedName("phone_number")
    val phoneNumber: Long? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("address")
    val address: String? = null
) : Parcelable
