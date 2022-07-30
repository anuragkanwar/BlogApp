package com.example.myblogapp.model.response

data class FullBlog(
    val authorName: String,
    val bookmarked: Boolean,
    val category: String,
    val comments: List<Comment>,
    val content: String,
    val createdAt: Long,
    val id: Int,
    val imgUrl: String,
    val likes: Long,
    val metaTitle: String,
    val published: Boolean,
    val publishedAt: Long,
    val summary: String,
    val title: String,
    val updatedAt: Long,
    val userid: Int
)