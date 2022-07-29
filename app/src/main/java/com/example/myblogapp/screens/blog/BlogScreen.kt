package com.example.myblogapp.screens.blog

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.navigation.NavController
import com.example.myblogapp.navigation.BlogScreens

@Composable
fun BlogScreen(
    navController: NavController,
    viewModel: BlogScreenViewModel,
    id: Int?
) {

    val blog by remember {
        viewModel.blog
    }

    Text(text = "$blog")

    Button(onClick = {
        navController.navigate(BlogScreens.CommentScreen.name + "/$id")
    }) {
        Text(text = "Comments")
    }


}
