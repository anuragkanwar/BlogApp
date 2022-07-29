package com.example.myblogapp.screens.entryScreen

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun EntryScreen(
    navController: NavController,
    viewModel: EntryScreenViewModel
) {
    Text(text = "EntryScreen")
}