package com.example.myblogapp

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myblogapp.navigation.BlogNavigation
import com.example.myblogapp.ui.theme.MyBlogAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        makeStatusBarTransparent()
        setContent {
            MyApp()
        }
    }
}

@Suppress("DEPRECATION")
fun Activity.makeStatusBarTransparent() {
    window.apply {
        clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        statusBarColor = android.graphics.Color.TRANSPARENT
    }
}

@Composable
fun MyApp(
) {
    MyBlogAppTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 24.dp),
            color = MaterialTheme.colors.background
        ) {
            BlogNavigation()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
}