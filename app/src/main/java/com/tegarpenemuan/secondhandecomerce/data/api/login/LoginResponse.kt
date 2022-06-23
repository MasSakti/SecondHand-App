package com.tegarpenemuan.secondhandecomerce.data.api.login

data class LoginResponse(
    val access_token: String,
    val email: String,
    val name: String,
    val id: String,
)