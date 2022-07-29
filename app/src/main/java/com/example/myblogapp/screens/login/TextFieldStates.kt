package com.example.myblogapp.screens.login

data class BlogTextFieldState(
    val text : String = "",
    val hintText : String = "",
    val isHintVisible : Boolean = true
)

data class BlogPasswordTextFieldState(
    val text : String = "",
    val hintText : String = "",
    val isHintVisible : Boolean = true,
    val passwordVisible : Boolean = true,
)
