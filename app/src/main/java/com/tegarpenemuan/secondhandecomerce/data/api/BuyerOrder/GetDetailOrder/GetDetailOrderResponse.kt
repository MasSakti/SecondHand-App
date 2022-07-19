package com.tegarpenemuan.secondhandecomerce.data.api.BuyerOrder.GetDetailOrder

data class GetDetailOrderResponse(
    val Product: Product,
    val User: User,
    val base_price: String,
    val price: Int,
    val buyer_name: String,
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
    val transaction_date: String,
    val updatedAt: String
)