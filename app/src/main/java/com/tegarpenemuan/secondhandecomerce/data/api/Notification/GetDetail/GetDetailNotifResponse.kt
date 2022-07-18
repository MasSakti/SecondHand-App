package com.tegarpenemuan.secondhandecomerce.data.api.Notification.GetDetail

data class GetDetailNotifResponse(
    val Product: Product,
    val User: User,
    val base_price: String,
    val bid_price: Any,
    val buyer_name: Any,
    val createdAt: String,
    val id: Int,
    val image_url: String,
    val notification_type: String,
    val order_id: Any,
    val product_id: Int,
    val product_name: String,
    val read: Boolean,
    val receiver_id: Int,
    val seller_name: String,
    val status: String,
    val transaction_date: Any,
    val updatedAt: String
)