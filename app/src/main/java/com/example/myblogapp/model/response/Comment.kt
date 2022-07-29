package com.example.myblogapp.model.response

data class Comment(
    val UserId: Int,
    val blogId: Int,
    val content: String,
    val id: Int,
    val likes: Int,
    val publishedAt: Long
)