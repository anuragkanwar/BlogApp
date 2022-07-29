package com.example.myblogapp.model.respond

data class RegisterUser(
    val email: String,
    val firstName: String,
    val lastLogin: Long,
    val lastName: String,
    val middleName: String,
    val mobile: String,
    val password: String,
    val registerAt: Long
)