package com.example.myblogapp.model.response

data class LoginResponse(
    val `data`: Data,
    val message: String,
    val success: Boolean
)