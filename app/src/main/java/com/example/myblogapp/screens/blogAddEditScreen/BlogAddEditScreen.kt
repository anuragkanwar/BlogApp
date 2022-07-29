package com.example.myblogapp.screens.blogAddEditScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myblogapp.components.TransparentHintTextField
import com.example.myblogapp.screens.login.LoginScreenViewModel

@Composable
fun BlogAddEditScreen(
    navController: NavController,
    viewModel: BlogAddEditViewModel
) {

    val titleState = viewModel.blogTitle.value
    val contentState = viewModel.blogContent.value
    val summaryState = viewModel.blogSummary.value
    val metaTitleState = viewModel.blogMetaTitle.value

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .padding(8.dp)
    ) {
        FilterItem() {
            viewModel.onEvent(BlogAddEditViewModel.BlogEvents.ChangeCategory(it))
        }

        TransparentHintTextField(
            text = titleState.text,
            hint = titleState.hintText,
            onValueChange = { viewModel.onEvent(BlogAddEditViewModel.BlogEvents.EnteredTitle(it)) },
            onFocusChange = {
                viewModel.onEvent(
                    BlogAddEditViewModel.BlogEvents.ChangeEnteredTitleFocus(
                        it
                    )
                )
            },
            isHintVisible = titleState.isHintVisible,
            singleLine = false,
            textStyle = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = Color.Black
            )
        )
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 3.dp),
            color = Color.LightGray,
            thickness = 1.dp
        )
        Spacer(modifier = Modifier.height(6.dp))


        TransparentHintTextField(
            text = metaTitleState.text,
            hint = metaTitleState.hintText,
            onValueChange = { viewModel.onEvent(BlogAddEditViewModel.BlogEvents.EnteredMetaTitle(it)) },
            onFocusChange = {
                viewModel.onEvent(
                    BlogAddEditViewModel.BlogEvents.ChangeEnteredMetaTitleFocus(
                        it
                    )
                )
            },
            isHintVisible = metaTitleState.isHintVisible,
            singleLine = false,
            textStyle = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = Color.Black
            )
        )
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 3.dp),
            color = Color.LightGray,
            thickness = 1.dp
        )
        Spacer(modifier = Modifier.height(6.dp))

        TransparentHintTextField(
            text = contentState.text,
            hint = contentState.hintText,
            onValueChange = { viewModel.onEvent(BlogAddEditViewModel.BlogEvents.EnteredContent(it)) },
            onFocusChange = {
                viewModel.onEvent(
                    BlogAddEditViewModel.BlogEvents.ChangeEnteredContentFocus(
                        it
                    )
                )
            },
            isHintVisible = contentState.isHintVisible,
            singleLine = false,
            textStyle = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = Color.Black
            )
        )
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 3.dp),
            color = Color.LightGray,
            thickness = 1.dp
        )
        Spacer(modifier = Modifier.height(6.dp))

        TransparentHintTextField(
            text = summaryState.text,
            hint = summaryState.hintText,
            onValueChange = { viewModel.onEvent(BlogAddEditViewModel.BlogEvents.EnteredSummary(it)) },
            onFocusChange = {
                viewModel.onEvent(
                    BlogAddEditViewModel.BlogEvents.ChangeEnteredSummaryFocus(
                        it
                    )
                )
            },
            isHintVisible = summaryState.isHintVisible,
            singleLine = false,
            textStyle = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = Color.Black
            )
        )
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 3.dp),
            color = Color.LightGray,
            thickness = 1.dp
        )
        Spacer(modifier = Modifier.height(6.dp))
    }
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