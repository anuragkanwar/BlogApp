package com.example.myblogapp.model.response

data class AllBlogsResponse(
    val `data`: List<MinBlog>,
    val message: String,
    val success: Boolean
)