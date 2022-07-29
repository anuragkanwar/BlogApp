package com.example.myblogapp.model.response

data class FullBlogResponse(
    val `data`: FullBlog,
    val message: String,
    val success: Boolean
)