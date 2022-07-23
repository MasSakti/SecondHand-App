package binar.and3.kelompok1.secondhand.data.api.buyer

import com.google.gson.annotations.SerializedName

data class PostBuyerBidRequest(
    @SerializedName("product_id" ) var productId : Int? = null,
    @SerializedName("bid_price"  ) var bidPrice  : Int? = null
)
