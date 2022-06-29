package com.tegarpenemuan.secondhandecomerce.data.api.getProductDetails

data class GetProductDetailsResponse(
    val base_price: Int,
    val categories: List<Category>,
    val created_at: String,
    val description: String,
    val id: Int,
    val image_name: String,
    val image_url: String,
    val location: String,
    val name: String,
    val updated_at: String,
    val user_id: Int
){
    data class Category(
        val id: Int,
        val name: String
    )
}