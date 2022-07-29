package com.example.myblogapp.model.respond

data class AddComment(
    val blogId: Int,
    val content: String,
    val likes: Int,
    val pusblishedAt: Long
)