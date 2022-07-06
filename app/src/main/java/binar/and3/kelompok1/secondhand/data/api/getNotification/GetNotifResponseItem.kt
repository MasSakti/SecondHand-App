package binar.and3.kelompok1.secondhand.data.api.getNotification

data class GetNotifResponseItem(
    val Product: Product,
    val User: User,
    val base_price: String,
    val bid_price: Int,
    val buyer_name: String,
    val createdAt: String,
    val id: Int,
    val image_url: String,
    val product_id: Int,
    val product_name: String,
    val read: Boolean,
    val receiver_id: Int,
    val seller_name: String,
    val status: String,
    val transaction_date: String,
    val updatedAt: String
)