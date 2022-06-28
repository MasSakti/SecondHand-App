package id.co.binar.secondhand.model.notification

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import id.co.binar.secondhand.model.seller.product.GetProductResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetNotifResponse(

	@SerializedName("transaction_date")
	val transactionDate: String? = null,

	@SerializedName("read")
	var read: Boolean? = null,

	@SerializedName("seller_name")
	val sellerName: String? = null,

	@SerializedName("updated_at")
	val updatedAt: String? = null,

	@SerializedName("receiver_id")
	val receiverId: Int? = null,

	@SerializedName("image_url")
	val imageUrl: String? = null,

	@SerializedName("product_id")
	val productId: Int? = null,

	@SerializedName("buyer_name")
	val buyerName: String? = null,

	@SerializedName("created_at")
	val createdAt: String? = null,

	@SerializedName("id")
	val id: Int? = null,

	@SerializedName("bid_price")
	val bidPrice: Int? = null,

	@SerializedName("status")
	val status: String? = null
) : Parcelable
