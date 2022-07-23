package binar.and3.kelompok1.secondhand.data.api.buyer

import com.google.gson.annotations.SerializedName

data class PostBuyerBidResponse(
    @SerializedName("id"                ) var id               : Int?    = null,
    @SerializedName("product_id"        ) var productId        : Int?    = null,
    @SerializedName("buyer_id"          ) var buyerId          : Int?    = null,
    @SerializedName("price"             ) var price            : Int?    = null,
    @SerializedName("transcaction_date" ) var transcactionDate : String? = null,
    @SerializedName("product_name"      ) var productName      : String? = null,
    @SerializedName("base_price"        ) var basePrice        : Int?    = null,
    @SerializedName("image_product"     ) var imageProduct     : String? = null,
    @SerializedName("status"            ) var status           : String? = null,
)