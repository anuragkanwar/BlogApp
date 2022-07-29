package com.example.myblogapp.model.respond

data class CreateBlog(
    val category: String,
    val content: String,
    val createdAt: Long,
    val imgUrl: String,
    val metaTitle: String,
    val published: Boolean,
    val publishedAt: Long,
    val summary: String,
    val title: String,
    val updatedAt: Long
)