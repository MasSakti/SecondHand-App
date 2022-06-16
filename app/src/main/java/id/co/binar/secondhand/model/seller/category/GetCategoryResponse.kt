package id.co.binar.secondhand.model.seller.category

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetCategoryResponse(

	val getCategoryResponse: List<GetCategoryResponseItem>? = null
) : Parcelable

@Parcelize
data class GetCategoryResponseItem(

	@SerializedName("createdAt")
	val createdAt: String? = null,

	@SerializedName("name")
	val name: String? = null,

	@SerializedName("id")
	val id: Int? = null,

	@SerializedName("updatedAt")
	val updatedAt: String? = null
) : Parcelable