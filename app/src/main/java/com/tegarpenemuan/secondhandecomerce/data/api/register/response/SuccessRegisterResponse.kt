package com.tegarpenemuan.secondhandecomerce.data.api.register.response

data class SuccessRegisterResponse(
    val address: String,
    val createdAt: String,
    val email: String,
    val full_name: String,
    val id: Int,
    val image_url: Any,
    val password: String,
    val phone_number: Long,
    val updatedAt: String
)