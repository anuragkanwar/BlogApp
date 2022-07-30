package com.example.myblogapp.screens.homescreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import coil.size.Size
import com.example.myblogapp.components.BlogCard
import com.example.myblogapp.navigation.BlogScreens
import com.example.myblogapp.screens.login.LoadingIndicator
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay


@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeScreenViewModel
) {

    val blogList by remember {
        viewModel.blogList
    }

    val loadError by remember {
        viewModel.loadError
    }

    val isLoading by remember {
        viewModel.isLoading
    }

    var currentFilter by remember {
        mutableStateOf("")
    }

    var isRefreshing by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(isRefreshing) {
        if (isRefreshing) {
            delay(3000)
            isRefreshing = false
        }
    }


    Column(
        modifier = Modifier.fillMaxSize()
            .padding(top = 25.dp,start = 8.dp,end = 8.dp,bottom = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 25.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Feed", style = MaterialTheme.typography.h3)
            myCard(viewModel = viewModel) {
                navController.navigate(BlogScreens.ProfileScreen.name)
            }
        }

        FilterItem() {
            currentFilter = it
            viewModel.loadBlogs(it)
        }

        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
            onRefresh = {
                isRefreshing = true
                viewModel.loadBlogs(currentFilter)
            }
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                state = rememberLazyGridState(),
                contentPadding = PaddingValues(10.dp)
            )
            {
                items(blogList.size) { Index ->
                    BlogCard(blogList[Index]) {
                        navController.navigate(BlogScreens.BlogScreen.name + "/${it}")
                    }
                }
            }
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if (isLoading) {
                    LoadingIndicator()
                }
                if (loadError.isNotEmpty()) {
                    ErrorRetryIndicator(error = loadError) {
                        viewModel.loadBlogs()
                    }
                }
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
fun myCard(
    viewModel: HomeScreenViewModel,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .size(60.dp)
            .clickable {
                onClick.invoke()
            },
        shape = CircleShape,
        backgroundColor = MaterialTheme.colors.background,
        elevation = 2.dp
    ) {
        ImageComposable(viewModel = viewModel)
    }
}

@Composable
fun ImageComposable(
    viewModel: HomeScreenViewModel
) {

    var image = ""
    LaunchedEffect(true) {
        image = viewModel.getImage()
    }

    if (image.isEmpty()) {
        image = "https://robohash.org/cdcd.jpg?set=set4"
    }


    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(image)
            .size(Size.ORIGINAL)
            .scale(Scale.FILL)
            .build(),
    )

    Image(
        painter = painter,
        contentDescription = "Image",
        modifier = Modifier
            .size(40.dp)
            .graphicsLayer {
                var rotationY = 180f
            },
        contentScale = ContentScale.Inside
    )
}


@Composable
fun FilterItem(
    onClick: (String) -> Unit
) {
    val categories = listOf(
        "All",
        "Travel",
        "Code",
        "Blog",
        "Food",
        "Work",
        "Nature",
        "LifeStyle",
        "Fashion",
        "Dance",
    )

    val isSelected = remember {
        mutableStateOf(0)
    }

    val lazyState = rememberLazyListState()

    LazyRow(
        modifier = Modifier.padding(horizontal = 12.dp),
        state = lazyState,
    ) {
        itemsIndexed(categories) { index, category ->
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .clickable {
                        isSelected.value = index
                        if (category == "All") {
                            onClick("")
                        } else {
                            onClick(category)
                        }
                    },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = category,
                    color = if (isSelected.value == index) Color.Black else Color.LightGray,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                AnimatedVisibility(isSelected.value == index) {
                    Icon(
                        imageVector = Icons.Filled.Circle,
                        contentDescription = "Selected",
                        modifier = Modifier
                            .size(10.dp)
                            .align(Alignment.End),
                        tint = Color.Black
                    )
                }
            }
            Spacer(modifier = Modifier.width(7.dp))
        }
    }
}