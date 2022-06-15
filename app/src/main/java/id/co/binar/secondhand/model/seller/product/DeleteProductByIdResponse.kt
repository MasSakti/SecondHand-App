package id.co.binar.secondhand.model.seller.product

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DeleteProductByIdResponse(

	@SerializedName("name")
	val name: String? = null,

	@SerializedName("message")
	val message: String? = null
) : Parcelable
