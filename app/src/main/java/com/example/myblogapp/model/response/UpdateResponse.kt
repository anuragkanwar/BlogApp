package com.example.myblogapp.model.response

data class UpdateResponse(
    val `data`: User,
    val message: String,
    val success: Boolean
)