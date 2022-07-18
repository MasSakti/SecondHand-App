package com.tegarpenemuan.secondhandecomerce.data.api.SellerOrder

data class SellerOrderResponseItem(
    val Product: Product,
    val User: UserX,
    val base_price: String,
    val buyer_id: Int,
    val createdAt: String,
    val id: Int,
    val image_product: String,
    val price: Int,
    val product_id: Int,
    val product_name: String,
    val status: String,
    val transaction_date: Any,
    val updatedAt: String
)