package com.example.myblogapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import coil.size.Size
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import java.util.*
import androidx.compose.foundation.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.myblogapp.model.response.MinBlog


@Composable
fun LottieComponent(
    asset: String,
    modifier: Modifier = Modifier,
    speed: Float = 1.5f,
    size: Dp = 100.dp
) {

    val composition by rememberLottieComposition(spec = LottieCompositionSpec.Asset(asset))
    LottieAnimation(
        composition = composition,
        iterations = Int.MAX_VALUE,
        modifier = Modifier
            .padding(8.dp)
            .size(size),
        speed = speed
    )
}

@Composable
fun TitleContent(
    modifier: Modifier = Modifier,
    text: String = "10-day Norway road trip itinerary in stunning Fjordland",
    maxLines: Int = 3
) {

    Text(
        text = text,
        modifier = modifier
            .fillMaxWidth()
            .padding(
                top = 2.dp,
                start = 10.dp,
                end = 6.dp,
                bottom = 10.dp
            ),
        color = Color.White,
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
        softWrap = true,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
    )
}

private fun getMonthFull(
    month: String
): String {
    return when (month) {
        "jan" -> "January"
        "feb" -> "February"
        "mar" -> "March"
        "apr" -> "April"
        "may" -> "May"
        "jun" -> "June"
        "jul" -> "July"
        "aug" -> "August"
        "sep" -> "September"
        "oct" -> "October"
        "nov" -> "November"
        "dec" -> "December"
        else -> "God Month"
    }
}

@Composable
fun DateContent(
    date: Date = Date(System.currentTimeMillis())
) {
    val blogDay = date.toString().split(' ')[0]
    val blogMonth = date.toString().split(' ')[1].lowercase()
    val blogDate = date.toString().split(' ')[2]
    val blogTime = date.toString().split(' ')[3]
    val blogYear = date.toString().split(' ').last()


    val displayDate = "${getMonthFull(blogMonth)} $blogDate, $blogYear"

    Text(
        text = displayDate,
        color = Color.White,
        fontSize = 18.sp,
        modifier = Modifier.padding(end = 8.dp)
    )
}

@Composable
fun BlogCard(
    blog: MinBlog,
    onClick: (Int) -> Unit = {}
) {

    val image = blog.imgUrl
//        "https://cdn.unenvironment.org/s3fs-public/styles/topics_content_promo/public/2021-05/alberta-2297204_1920.jpg?itok=GazAjNLg"

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(image)
            .size(Size.ORIGINAL)
            .transformations()
            .scale(Scale.FILL)
            .build(),
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .clickable {
                //pass blogId
                onClick(blog.id)
            },
        shape = RoundedCornerShape(25.dp),
        elevation = 0.dp
    ) {


        Image(
            painter = painter,
            contentDescription = "Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
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
                        startY = 100f
                    )
                )
        )

        Box(
            modifier = Modifier.fillMaxSize(),
        ) {

            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp),
            ) {
                DateContent(
                    date = Date(blog.publishedAt)
                )
            }

            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.TopStart)
                    .background(Color.Gray.copy(0.3f), CircleShape)
                    .clip(CircleShape)
                    .size(35.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Place,
                    contentDescription = "Camera",
                    tint = Color.White,
                    modifier = Modifier.size(22.dp)
                )
            }


            Box(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .padding(8.dp)
                    .align(Alignment.BottomStart),
            ) {
                TitleContent(
                    modifier = Modifier,
                    text = blog.title,
                    maxLines = 3
                )
            }
        }


    }

}

@Composable
fun MyButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true
) {

    Button(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        enabled = enabled,
        modifier = modifier.padding(10.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Black,
            contentColor = Color.White
        )
    ) {
        Text(text = text, modifier = Modifier.padding(6.dp))
    }
}

@Composable
fun TransparentHintTextField(
    text: String,
    hint: String,
    modifier: Modifier = Modifier,
    isHintVisible: Boolean = true,
    onValueChange: (String) -> Unit,
    textStyle: TextStyle = TextStyle(),
    singleLine: Boolean = false,
    onFocusChange: (FocusState) -> Unit,
    onPasswordChange: () -> Unit = {},
    isPasswordField: Boolean = false,
    isPasswordVisible: Boolean = true
) {
    Box(
        modifier = modifier.fillMaxWidth(),
    ) {
        BasicTextField(
            value = text,
            onValueChange = onValueChange,
            singleLine = singleLine,
            textStyle = textStyle,
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = modifier
                .fillMaxWidth()
                .align(Alignment.CenterStart)
                .padding(16.dp)
                .onFocusChanged {
                    onFocusChange(it)
                }
        )
        if (isHintVisible) {
            Text(
                text = hint,
                style = textStyle,
                color = Color.LightGray,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .align(Alignment.CenterStart)
            )
        }
        if (isPasswordField) {
            val image = if (isPasswordVisible)
                Icons.Outlined.Visibility
            else Icons.Outlined.VisibilityOff
            val description = if (isPasswordVisible) "Hide password" else "Show password"
            Box(
                modifier = Modifier.align(Alignment.CenterEnd),
                contentAlignment = Alignment.Center
            )
            {
                IconButton(onClick = { onPasswordChange() }) {
                    Icon(
                        imageVector = image,
                        description,
                        tint = MaterialTheme.colors.onBackground,
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
                }
            }
        }
    }
}



