package binar.and3.kelompok1.secondhand.data.api.seller

import com.google.gson.annotations.SerializedName

data class GetSellerOderByIdResponse(
    @SerializedName("id"               ) var id              : Int?     = null,
    @SerializedName("product_id"       ) var productId       : Int?     = null,
    @SerializedName("buyer_id"         ) var buyerId         : Int?     = null,
    @SerializedName("price"            ) var price           : Int?     = null,
    @SerializedName("transaction_date" ) var transactionDate : String?  = null,
    @SerializedName("product_name"     ) var productName     : String?  = null,
    @SerializedName("base_price"       ) var basePrice       : Int?     = null,
    @SerializedName("image_product"    ) var imageProduct    : String?  = null,
    @SerializedName("status"           ) var status          : String?  = null,
    @SerializedName("createdAt"        ) var createdAt       : String?  = null,
    @SerializedName("updatedAt"        ) var updatedAt       : String?  = null,
    @SerializedName("Product"          ) var product         : Product? = Product(),
    @SerializedName("User"             ) var user            : User?    = User()
) {
    data class User (

        @SerializedName("id"           ) var id          : Int?    = null,
        @SerializedName("full_name"    ) var fullName    : String? = null,
        @SerializedName("email"        ) var email       : String? = null,
        @SerializedName("phone_number" ) var phoneNumber : String? = null,
        @SerializedName("address"      ) var address     : String? = null,
        @SerializedName("image_url"    ) var imageUrl    : String? = null,
        @SerializedName("city"         ) var city        : String? = null

    )

    data class Product (

        @SerializedName("name"        ) var name        : String? = null,
        @SerializedName("description" ) var description : String? = null,
        @SerializedName("base_price"  ) var basePrice   : Int?    = null,
        @SerializedName("image_url"   ) var imageUrl    : String? = null,
        @SerializedName("image_name"  ) var imageName   : String? = null,
        @SerializedName("location"    ) var location    : String? = null,
        @SerializedName("user_id"     ) var userId      : Int?    = null,
        @SerializedName("status"      ) var status      : String? = null,
        @SerializedName("User"        ) var user        : User?   = User()

    ) {
        data class User (

            @SerializedName("id"           ) var id          : Int?    = null,
            @SerializedName("full_name"    ) var fullName    : String? = null,
            @SerializedName("email"        ) var email       : String? = null,
            @SerializedName("phone_number" ) var phoneNumber : String? = null,
            @SerializedName("address"      ) var address     : String? = null,
            @SerializedName("image_url"    ) var imageUrl    : String? = null,
            @SerializedName("city"         ) var city        : String? = null

        )
    }
}