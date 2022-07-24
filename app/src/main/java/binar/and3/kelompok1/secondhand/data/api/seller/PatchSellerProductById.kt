package binar.and3.kelompok1.secondhand.data.api.seller

import com.google.gson.annotations.SerializedName

data class PatchSellerProductById(
    @SerializedName("id"          ) var id          : Int?    = null,
    @SerializedName("name"        ) var name        : String? = null,
    @SerializedName("description" ) var description : String? = null,
    @SerializedName("base_price"  ) var basePrice   : Int?    = null,
    @SerializedName("image_url"   ) var imageUrl    : String? = null,
    @SerializedName("image_name"  ) var imageName   : String? = null,
    @SerializedName("location"    ) var location    : String? = null,
    @SerializedName("user_id"     ) var userId      : Int?    = null,
    @SerializedName("created_at"  ) var createdAt   : String? = null,
    @SerializedName("updated_at"  ) var updatedAt   : String? = null
)
