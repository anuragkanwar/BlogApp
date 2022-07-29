package com.example.myblogapp.model.response

data class CommentWithUser(
    val UserId: Int,
    val UserImage: String,
    val UserName: String,
    val blogId: Int,
    val content: String,
    val id: Int,
    val likes: Int,
    val publishedAt: Long
)