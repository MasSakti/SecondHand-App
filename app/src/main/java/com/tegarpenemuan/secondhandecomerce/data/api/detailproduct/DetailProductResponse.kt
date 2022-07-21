package com.tegarpenemuan.secondhandecomerce.data.api.detailproduct

data class DetailProductResponse(
    val Categories: List<Category>,
    val base_price: Int,
    val createdAt: String,
    val description: String,
    val id: Int,
    val image_name: String,
    val image_url: String,
    val location: String,
    val name: String,
    val status: String,
    val updatedAt: String,
    val user_id: Int
) {
    data class Category(
        val id: Int,
        val name: String
    )
}