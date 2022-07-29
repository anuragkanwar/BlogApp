package com.example.myblogapp.screens.commentScreen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import coil.size.Size
import com.example.myblogapp.components.BlogCard
import com.example.myblogapp.components.DateContent
import com.example.myblogapp.navigation.BlogScreens
import com.example.myblogapp.screens.homescreen.ErrorRetryIndicator
import com.example.myblogapp.screens.login.LoadingIndicator
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import java.util.*

@Composable
fun CommentScreen(
    navController: NavController,
    viewModel: CommentScreenViewModel,
    id: Int?
) {

    val loadError by remember {
        viewModel.loadError
    }

    val isLoading by remember {
        viewModel.isLoading
    }

    var isRefreshing by remember {
        mutableStateOf(false)
    }

    val comment by remember {
        viewModel.comment
    }

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
        onRefresh = {
            isRefreshing = true
            viewModel.getAllComments(id!!)
        }
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            state = rememberLazyGridState(),
            contentPadding = PaddingValues(10.dp)
        )
        {
            items(comment.size) { Index ->
                CommentItem(
                    image = comment[Index].UserImage,
                    name = comment[Index].UserName,
                    date = Date(comment[Index].publishedAt),
                    content = comment[Index].content,
                )
            }
            item {
                CommentBar(viewModel = viewModel)
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
                    viewModel.getAllComments(id!!)
                }
            }
        }
    }


}

@Composable
fun CommentItem(
    image: String = "https://cdn.unenvironment.org/s3fs-public/styles/topics_content_promo/public/2021-05/alberta-2297204_1920.jpg?itok=GazAjNLg",
    name: String = "Alex Suprun",
    date: Date = Date(System.currentTimeMillis()),
    content: String = "@Brooke Cagle Oh ok that's kinda what i was thinking."
) {

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(image)
            .size(Size.ORIGINAL)
            .transformations()
            .scale(Scale.FILL)
            .build(),
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
//            .height(200.dp),
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(10.dp))
        ) {
            Image(
                painter = painter,
                contentDescription = "Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
        }
        Spacer(modifier = Modifier.width(15.dp))

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .weight(0.7f),
            horizontalAlignment = Alignment.Start,
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = name,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.width(12.dp))
                DateContent(date = date)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = content, color = Color.LightGray, fontSize = 21.sp)

            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.End),
                contentAlignment = Alignment.Center
            ) {
                IconButton(onClick = {

                }) {
                    Icons.Outlined.Delete
                }
            }
        }
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CommentBar(
    viewModel: CommentScreenViewModel
) {

    var text by remember {
        mutableStateOf("")
    }

    val keyboardController = LocalSoftwareKeyboardController.current


    OutlinedTextField(
        value = text,
        onValueChange = {
            text = it.trim()
        },
        label = {
            Text(
                text = "Enter Comment...",
            )
        },
        textStyle = TextStyle(
            fontSize = 20.sp,
            color = Color.White,
        ),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            cursorColor = Color.White
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone =
        {
            keyboardController?.hide()
        }),
        shape = RoundedCornerShape(35.dp),
        modifier = Modifier.fillMaxWidth(),
        trailingIcon = {
            IconButton(onClick = {
                if (text.isNotEmpty()) {
                    viewModel.addComment(text)
                }
            }) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Send",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    )
}