package com.example.myblogapp.model.response

data class User(
    val email: String,
    val firstName: String,
    val id: Int,
    val imgUrl: String,
    val intro: String,
    val lastLogin: Long,
    val lastName: String,
    val middleName: String,
    val mobile: String,
    val password: String,
    val profile: String,
    val registerAt: Long
)