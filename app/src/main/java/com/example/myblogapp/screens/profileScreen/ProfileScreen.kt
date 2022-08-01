package com.example.myblogapp.screens.profileScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.ArrowRight
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import coil.size.Size
import com.example.myblogapp.navigation.BlogScreens
import com.example.myblogapp.screens.login.LoginScreenViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileScreenViewModel
) {

    val isLoading = remember {
        mutableStateOf(false)
    }
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is ProfileScreenViewModel.UiEvent.Loading -> {
                    isLoading.value = true
                }
                is ProfileScreenViewModel.UiEvent.ShowSnackbar -> {
                    isLoading.value = false
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message + "Try again Later"
                    )
                }
                is ProfileScreenViewModel.UiEvent.Success -> {
                    isLoading.value = false
                    navController.navigate(BlogScreens.SplashScreen.name) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                }
            }
        }
    }


    var image by remember {
        mutableStateOf("")
    }

    var name by remember {
        mutableStateOf("")
    }
    LaunchedEffect(true) {
        image = viewModel.getUserImage()
        name = viewModel.getUserName()
    }


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
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 30.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        Card(
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .align(Alignment.CenterHorizontally)
        ) {
            Image(
                painter = painter,
                contentDescription = "Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
        }

        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = name,
            color = Color.Black,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(10.dp))

        ProfileItem(icon = Icons.Default.Bookmark, text = "Bookmarks") {

        }

        ProfileItem(icon = Icons.Default.Camera, text = "My Blogs") {

        }

        ProfileItem(icon = Icons.Default.DonutLarge, text = "My Published Blog") {

        }

        ProfileItem(icon = Icons.Default.Create, text = "New Blog") {
            navController.navigate(BlogScreens.AddEditBlogScreen.name)
        }

        Text(
            text = "Log out",
            color = Color.Red,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clickable {
                    viewModel.logout()
                }

        )

    }
}

@Composable
fun ProfileItem(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.LightGray.copy(0.2f), RoundedCornerShape(10.dp))
            .clickable {
                onClick()
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            tint = Color.Black,
            modifier = Modifier.padding(10.dp)
        )

        Text(
            text = text,
            color = Color.Black,
            fontSize = 22.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(10.dp)
        )

        Icon(
            imageVector = Icons.Outlined.ArrowRight,
            contentDescription = text,
            tint = Color.Black,
            modifier = Modifier.padding(10.dp)
        )
    }
}