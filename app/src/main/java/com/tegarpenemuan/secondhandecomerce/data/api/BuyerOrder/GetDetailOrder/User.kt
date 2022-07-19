package com.tegarpenemuan.secondhandecomerce.data.api.BuyerOrder.GetDetailOrder

data class User(
    val address: String,
    val city: String,
    val email: String,
    val full_name: String,
    val id: Int,
    val image_url: String,
    val phone_number: String
)