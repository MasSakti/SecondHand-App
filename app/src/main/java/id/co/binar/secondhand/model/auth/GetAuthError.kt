package id.co.binar.secondhand.model.auth

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetAuthError(

	@SerializedName("name")
	val name: String? = null,

	@SerializedName("message")
	val message: String? = null
) : Parcelable
