package com.example.myblogapp.model.respond

data class UpdateComment(
    val id: Int,
    val blogId: Int,
    val UserId: Int = 0,
    val content: String,
    val likes: Long,
    val publishedAt: Long
)