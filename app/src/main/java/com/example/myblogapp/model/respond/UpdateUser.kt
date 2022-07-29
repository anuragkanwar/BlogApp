package com.example.myblogapp.model.respond

data class UpdateUser(
    val email: String,
    val firstName: String,
    val imageUrl: String,
    val intro: String,
    val lastName: String,
    val middleName: String,
    val mobile: String,
    val profile: String
)