package com.example.myblogapp.model.response

data class AllCommentResponse(
    val data: List<CommentWithUser>,
    val message: String,
    val success: Boolean
)
