package com.example.myblogapp.model.response

data class CommentResponse(
    val data: CommentWithUser,
    val message: String,
    val success: Boolean
)