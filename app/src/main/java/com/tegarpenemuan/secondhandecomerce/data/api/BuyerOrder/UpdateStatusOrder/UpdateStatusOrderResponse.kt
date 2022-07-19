package com.tegarpenemuan.secondhandecomerce.data.api.BuyerOrder.UpdateStatusOrder

data class UpdateStatusOrderResponse(
    val base_price: Int,
    val buyer_id: Int,
    val createdAt: String,
    val id: Int,
    val image_product: String,
    val price: Int,
    val product_id: Int,
    val product_name: String,
    val status: String,
    val transcaction_date: String,
    val updatedAt: String,
    val user_id: Int
)