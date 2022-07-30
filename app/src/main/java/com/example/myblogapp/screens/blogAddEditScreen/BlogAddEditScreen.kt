package com.example.myblogapp.screens.blogAddEditScreen

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Publish
import androidx.compose.material.icons.outlined.Camera
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myblogapp.components.TransparentHintTextField
import com.example.myblogapp.screens.login.LoginScreenViewModel
import com.example.myblogapp.utils.AppColors
import kotlinx.coroutines.flow.collectLatest

@Composable
fun BlogAddEditScreen(
    navController: NavController,
    viewModel: BlogAddEditViewModel
) {

    val titleState = viewModel.blogTitle.value
    val contentState = viewModel.blogContent.value
    val summaryState = viewModel.blogSummary.value
    val metaTitleState = viewModel.blogMetaTitle.value

    val scaffoldState = rememberScaffoldState()

    val isLoading = remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is BlogAddEditViewModel.BlogScreenUiEvent.Loading -> {
                    isLoading.value = true
                }
                is BlogAddEditViewModel.BlogScreenUiEvent.ShowSnackbar -> {
                    isLoading.value = false
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                is BlogAddEditViewModel.BlogScreenUiEvent.Success -> {
                    isLoading.value = false
                    navController.navigateUp()
                }
            }
        }
    }

    Scaffold(floatingActionButton = {
        FloatingActionButton(
            onClick = {
                if (titleState.text.isNotEmpty() && contentState.text.isNotEmpty()) {
                    viewModel.onEvent(BlogAddEditViewModel.BlogEvents.Publish)
                } else {
                    Toast.makeText(
                        context,
                        "Title and content can't be empty",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            backgroundColor = AppColors.black,
            modifier = Modifier.size(80.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Publish,
                contentDescription = "Add Blog",
                modifier = Modifier.size(35.dp),
                tint = Color.White
            )
        }
    },
        scaffoldState = scaffoldState,
        snackbarHost = {
            SnackbarHost(it) { data ->
                Snackbar(
                    actionColor = Color.White,
                    snackbarData = data,
                    backgroundColor = AppColors.black,
                    contentColor = Color.White
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(it)
                .fillMaxSize()
                .padding(8.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 7.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Row(
                    modifier = Modifier
                        .padding(top = 16.dp, start = 16.dp)
                        .background(AppColors.black, RoundedCornerShape(18.dp))
                        .clickable {
                            navController.navigateUp()
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier.padding(5.dp)
                    )
                    Text(
                        "BACK",
                        color = Color.White,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .padding(top = 5.dp, bottom = 5.dp, start = 2.dp, end = 10.dp)
                    )
                }

                Icon(
                    imageVector = Icons.Outlined.CheckCircle,
                    contentDescription = "Done Button",
                    modifier = Modifier
                        .clickable {
                            if (titleState.text
                                    .trim()
                                    .isNotEmpty()
                            ) {
                                viewModel.onEvent(BlogAddEditViewModel.BlogEvents.Save)
                            } else {
                                Toast
                                    .makeText(context, "Empty Fields", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                        .size(30.dp),
                    tint = AppColors.black
                )
            }

            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                PickImageFromGallery()
            }
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 3.dp),
                color = Color.LightGray,
                thickness = 1.dp
            )
            Spacer(modifier = Modifier.height(3.dp))

            FilterItem() {
                viewModel.onEvent(BlogAddEditViewModel.BlogEvents.ChangeCategory(it))
            }

            Text(
                text = "Title",
                color = Color.LightGray,
                fontSize = 18.sp,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 3.dp)
            )
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
            Spacer(modifier = Modifier.height(3.dp))

            Text(
                text = "MetaHint",
                color = Color.LightGray,
                fontSize = 18.sp,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 3.dp)
            )
            TransparentHintTextField(
                text = metaTitleState.text,
                hint = metaTitleState.hintText,
                onValueChange = {
                    viewModel.onEvent(
                        BlogAddEditViewModel.BlogEvents.EnteredMetaTitle(
                            it
                        )
                    )
                },
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
                    fontSize = 18.sp,
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
            Spacer(modifier = Modifier.height(3.dp))

            Text(
                text = "Content",
                color = Color.LightGray,
                fontSize = 18.sp,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 3.dp)
            )
            TransparentHintTextField(
                text = contentState.text,
                hint = contentState.hintText,
                onValueChange = {
                    viewModel.onEvent(
                        BlogAddEditViewModel.BlogEvents.EnteredContent(
                            it
                        )
                    )
                },
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
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = "Summary",
                color = Color.LightGray,
                fontSize = 18.sp,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 3.dp)
            )
            TransparentHintTextField(
                text = summaryState.text,
                hint = summaryState.hintText,
                onValueChange = {
                    viewModel.onEvent(
                        BlogAddEditViewModel.BlogEvents.EnteredSummary(
                            it
                        )
                    )
                },
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
}

@Composable
fun PickImageFromGallery() {
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val context = LocalContext.current

    val bitmap = remember {
        mutableStateOf<Bitmap?>(null)
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
    ) { uri: Uri? ->
        imageUri = uri
    }

    Box(
        modifier = Modifier
            .size(400.dp)
            .background(Color.LightGray.copy(0.2f))
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        imageUri?.let {
            if (Build.VERSION.SDK_INT < 28) {
                bitmap.value = MediaStore.Images
                    .Media.getBitmap(context.contentResolver, it)
            } else {
                val source = ImageDecoder.createSource(context.contentResolver, it)
                bitmap.value = ImageDecoder.decodeBitmap(source)
            }


            bitmap.value?.let { btm ->
                Image(
                    bitmap = btm.asImageBitmap(), contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )
            }
        }


        IconButton(
            onClick = { launcher.launch("image/*") },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(12.dp)
                .background(Color.Transparent)
        ) {
            Icon(
                imageVector = Icons.Outlined.Camera,
                contentDescription = "Pick Image",
                tint = Color.Black
            )
        }
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