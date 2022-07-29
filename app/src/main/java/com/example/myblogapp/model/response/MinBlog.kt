package com.example.myblogapp.model.response

data class MinBlog(
    val id: Int,
    val imgUrl: String,
    val publishedAt: Long,
    val title: String
)