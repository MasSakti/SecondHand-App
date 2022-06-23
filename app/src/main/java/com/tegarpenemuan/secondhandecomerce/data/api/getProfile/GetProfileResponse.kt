package com.tegarpenemuan.secondhandecomerce.data.api.getProfile

data class GetProfileResponse(
    val address: String,
    val city: Any,
    val createdAt: String,
    val email: String,
    val full_name: String,
    val id: Int,
    val image_url: String,
    val password: String,
    val phone_number: String,
    val updatedAt: String
)