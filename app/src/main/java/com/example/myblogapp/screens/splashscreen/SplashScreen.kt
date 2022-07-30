package com.example.myblogapp.screens.splashscreen

import android.util.Log
import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.myblogapp.components.LottieComponent
import com.example.myblogapp.navigation.BlogScreens
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController, viewModel: SplashScreenViewModel) {

    val scale = remember {
        Animatable(0f)
    }


    LaunchedEffect(key1 = true, block = {
        scale.animateTo(
            targetValue = 0.9f,
            animationSpec = tween(
                durationMillis = 800,
                easing = {
                    OvershootInterpolator(8f)
                        .getInterpolation(it)
                }
            )
        )
        val ok = viewModel.checkUser()
        Log.d("TAG", "$ok")
        delay(1000)
        if (ok) {
            navController.navigate(BlogScreens.HomeScreen.name) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        } else {
            navController.navigate(BlogScreens.EntryScreen.name) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }

    })



    Surface(
        modifier = Modifier
            .padding(15.dp)
            .scale(scale.value),
        color = MaterialTheme.colors.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(4.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LottieComponent(asset = "splashcamera.json")
            Text(
                text = "Blog",
                modifier = Modifier.padding(3.dp),
                style = MaterialTheme.typography.h4,
                color = MaterialTheme.colors.onBackground
            )
        }
    }
}