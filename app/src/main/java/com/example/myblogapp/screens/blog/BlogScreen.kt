package com.example.myblogapp.screens.blog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import androidx.compose.ui.modifier.modifierLocalConsumer
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

    val image = blog!!.imgUrl

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
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ){
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Icon",
                        tint = Color.White
                    )
                    Text(text = blog.likes.toString(), modifier = Modifier.padding(4.dp), color = Color.White)
                }

                Icon(
                    imageVector = Icons.Default.SwipeDownAlt,
                    contentDescription = "Icon",
                    tint = Color.White,
                    modifier = Modifier.offset(x = (-22).dp)
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
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            ),
            softWrap = true
        )

        Text(
            text = blog.metaTitle, style = MaterialTheme.typography.body2.copy(
                fontWeight = FontWeight.ExtraBold,
                fontSize = 22.sp
            ),
            modifier = Modifier.padding(vertical = 5.dp, horizontal = 12.dp)
        )

        Text(
            text = blog.content, style = MaterialTheme.typography.body2.copy(
                fontSize = 16.sp
            ),
            modifier = Modifier.padding(vertical = 5.dp, horizontal = 12.dp)
        )


        Text(
            text = blog.summary,
            style = MaterialTheme.typography.caption,
            modifier = Modifier.padding(vertical = 5.dp, horizontal = 12.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.CameraEnhance,
                    contentDescription = "Camera Icon",
                    modifier = Modifier.size(18.dp)
                )
                Text(text = blog.authorName, modifier = Modifier.padding(start = 4.dp))
            }

            IconButton(onClick = {
                navController.navigate(BlogScreens.CommentScreen.name + "/$id")
            }) {
                Icon(imageVector = Icons.Default.Comment, contentDescription = "Comment icon")
            }
        }

    }
}
