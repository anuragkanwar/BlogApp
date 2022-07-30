package com.example.myblogapp.screens.blog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraEnhance
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.Healing
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import coil.size.Size
import com.example.myblogapp.model.response.FullBlog
import com.example.myblogapp.navigation.BlogScreens
import com.example.myblogapp.screens.login.LoadingIndicator

@Composable
fun BlogScreen(
    navController: NavController,
    viewModel: BlogScreenViewModel,
    id: Int?
) {

    val isLoading by remember {
        viewModel.isLoading
    }
    val loadError by remember {
        viewModel.loadError
    }
    val blog by remember {
        viewModel.blog
    }

    if (!isLoading) {
        ImageParallaxScroll(blog, navController, id)
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            LoadingIndicator()
        }
        if (loadError.isNotEmpty()) {
            com.example.myblogapp.screens.homescreen.ErrorRetryIndicator(error = loadError) {
                viewModel.getBlog(id)
            }
        }
    }
}


@Composable
fun ErrorRetryIndicator(
    error: String,
    onRetry: () -> Unit
) {
    Column {
        Text(text = error, color = Color.Red, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { onRetry() },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Retry")
        }
    }
}

@Composable
fun ImageParallaxScroll(
    blog: FullBlog?,
    navController: NavController,
    id: Int?
) {
    val scrollState = rememberScrollState()

    val image = "https://cdn.wallpapersafari.com/35/77/17pYHc.jpg"

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(image)
            .size(Size.ORIGINAL)
            .transformations()
            .scale(Scale.FILL)
            .build(),
    )


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(700.dp)
                .background(
                    Color.White, shape = RoundedCornerShape(
                        bottomStart = 16.dp,
                        bottomEnd = 16.dp
                    )
                )
                .clip(
                    shape = RoundedCornerShape(
                        bottomStart = 16.dp,
                        bottomEnd = 16.dp
                    )
                )
                .graphicsLayer {
                    alpha = 1f - ((scrollState.value.toFloat() / scrollState.maxValue) * 1.5f)
                    translationY = 0.5f * scrollState.value
                },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter,
                contentDescription = "tiger parallax",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(
                        shape = RoundedCornerShape(
                            bottomStart = 16.dp,
                            bottomEnd = 16.dp
                        )
                    )
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black
                            ),
                            startY = 250f
                        )
                    )
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
                    .align(Alignment.BottomCenter)
                    .height(100.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Healing,
                    contentDescription = "Icon",
                    tint = Color.White
                )
                Text(
                    "SCROLL DOWN", style = MaterialTheme.typography.caption.copy(
                        fontWeight = FontWeight.Bold
                    ), color = Color.White
                )
                Icon(
                    imageVector = Icons.Outlined.Bookmark,
                    contentDescription = "Add bookmark",
                    tint = Color.White
                )
            }
        }
        Text(
            text = blog!!.title,
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            ),
            softWrap = true
        )

        Text(
            text = blog.metaTitle, style = MaterialTheme.typography.body2.copy(
                fontWeight = FontWeight.ExtraBold
            )
        )

        Text(text = blog.content, style = MaterialTheme.typography.body2)

        Text(text = blog.summary, style = MaterialTheme.typography.caption)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row {
                Icon(imageVector = Icons.Default.CameraEnhance, contentDescription = "Camera Icon")
                Text(text = blog.authorName)
            }

            IconButton(onClick = {
                navController.navigate(BlogScreens.CommentScreen.name + "/$id")
            }) {
                Icon(imageVector = Icons.Default.Comment, contentDescription = "Comment icon")
            }
        }

    }
}
