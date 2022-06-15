package id.co.binar.secondhand.model.seller.banner

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetBannerByIdResponse(

	@SerializedName("image_name")
	val imageName: String? = null,

	@SerializedName("createdAt")
	val createdAt: String? = null,

	@SerializedName("user_id")
	val userId: Int? = null,

	@SerializedName("image_url")
	val imageUrl: String? = null,

	@SerializedName("name")
	val name: String? = null,

	@SerializedName("id")
	val id: Int? = null,

	@SerializedName("updatedAt")
	val updatedAt: String? = null
) : Parcelable
