package com.example.projectgroup2.data.api.auth.login

data class LoginResponse(
    val access_token: String,
    val email: String,
    val name: String
)