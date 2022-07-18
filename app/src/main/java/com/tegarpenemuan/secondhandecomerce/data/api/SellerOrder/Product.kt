package com.tegarpenemuan.secondhandecomerce.data.api.SellerOrder

data class Product(
    val User: UserX,
    val base_price: Int,
    val description: String,
    val image_name: String,
    val image_url: String,
    val location: String,
    val name: String,
    val status: String,
    val user_id: Int
)