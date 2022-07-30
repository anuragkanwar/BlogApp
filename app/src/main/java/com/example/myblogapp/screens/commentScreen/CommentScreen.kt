package com.example.myblogapp.screens.commentScreen

import android.widget.Space
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
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
import com.example.myblogapp.components.DateContent
import com.example.myblogapp.screens.homescreen.ErrorRetryIndicator
import com.example.myblogapp.screens.login.LoadingIndicator
import com.example.myblogapp.utils.AppColors
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay
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


    LaunchedEffect(isRefreshing) {
        if (isRefreshing) {
            delay(3000)
            isRefreshing = false
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
            onRefresh = {
                isRefreshing = true
                viewModel.getAllComments(id!!)
            },
            modifier = Modifier.weight(1f)
        ) {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 28.dp, start = 16.dp, end = 16.dp),
                reverseLayout = true
            ) {
                items(comment.size) { Index ->
                    CommentItem(
                        image = comment[Index].UserImage,
                        name = comment[Index].UserName,
                        date = Date(comment[Index].publishedAt),
                        content = comment[Index].content,
                        viewModel = viewModel,
                        id = comment[Index].id
                    )
                    Divider(
                        color = Color.DarkGray,
                        thickness = 1.dp
                    )
                }
//                item {
//                    Spacer(modifier = Modifier.height(12.dp))
//                    CommentBar(
//                        viewModel = viewModel
//                    )
//                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            CommentBar(
                viewModel = viewModel
            )
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

@Composable
fun CommentItem(
    image: String = "https://cdn.unenvironment.org/s3fs-public/styles/topics_content_promo/public/2021-05/alberta-2297204_1920.jpg?itok=GazAjNLg",
    name: String = "Alex Suprun",
    date: Date,
    content: String = "@Brooke Cagle Oh ok that's kinda what i was thinking.",
    viewModel: CommentScreenViewModel,
    id: Int
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
            .wrapContentHeight()
            .padding(8.dp),
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                DateContent(date = date, color = Color.LightGray, size = 12)
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = content, style = MaterialTheme.typography.caption)

//            Box(
//                modifier = Modifier
//                    .padding(8.dp)
//                    .align(Alignment.End),
//                contentAlignment = Alignment.Center
//            ) {
//                Icon(
//                    imageVector = Icons.Outlined.Delete,
//                    contentDescription = "Delete",
//                    tint = Color.White,
//                    modifier = Modifier
//                        .clickable {
//
//                        }
//                        .size(24.dp)
//                )
//            }
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
            text = it
        },
        modifier = Modifier.fillMaxWidth(),
        label = {
            Text(
                text = "Enter Comment...",
            )
        },
        textStyle = TextStyle(
            fontSize = 20.sp,
        ),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = Color.Transparent,
            cursorColor = Color.Black,
            focusedLabelColor = Color.Black,
            unfocusedLabelColor = Color.Gray,
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Gray
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
        trailingIcon = {
            IconButton(onClick = {
                if (text.isNotEmpty()) {
                    viewModel.addComment(text.trim())
                    text = ""
                }
            }) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Send",
                    tint = Color.Black,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    )
}