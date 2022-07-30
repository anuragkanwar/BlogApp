package com.example.myblogapp.screens.entryScreen

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
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import coil.size.Size
import com.example.myblogapp.navigation.BlogScreens
import com.example.myblogapp.screens.homescreen.ImageComposable
import com.example.myblogapp.utils.AppColors

@Composable
fun EntryScreen(
    navController: NavController,
    viewModel: EntryScreenViewModel
) {
    val image = "https://cdn.wallpapersafari.com/35/77/17pYHc.jpg"

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(image)
            .size(Size.ORIGINAL)
            .transformations()
            .scale(Scale.FILL)
            .build(),
    )


    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painter,
                contentDescription = "Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
        }

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

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 22.dp)
                .align(Alignment.BottomStart),
        ) {

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Hello, I am", color = Color.White, fontSize = 40.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.padding(5.dp)
                )
                Text(
                    text = "Anurag Kanwar", color = Color.White, fontSize = 42.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.padding(5.dp)
                )

                Text(
                    text = "They should be encouraged,praised and if possible " +
                            "given red wine and chocolates",
                    color = Color.White, fontSize = 21.sp,
                    softWrap = true,
                    maxLines = 3,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier
                        .wrapContentWidth()
                        .height(50.dp)
                        .background(Color.White, RoundedCornerShape(30.dp))
                        .clip(RoundedCornerShape(30.dp))
                        .clickable {
                            navController.navigate(BlogScreens.SignUpScreen.name)
                        },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "SIGN UP",
                        color = AppColors.black,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(start = 13.dp)
                    )

                    Card(
                        modifier = Modifier
                            .padding(5.dp)
                            .size(45.dp),
                        backgroundColor = AppColors.black,
                        shape = CircleShape,
                        elevation = 0.dp
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowForward,
                            contentDescription = "SignUp",
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    color = Color.White,
                                    fontSize = 20.sp,
                                )
                            ) {
                                append("Already have an account?")
                            }
                            withStyle(
                                style = SpanStyle(
                                    color = AppColors.blue,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            ) {
                                append("Sign in!")
                            }
                        },
                        modifier = Modifier.clickable {
                            navController.navigate(BlogScreens.SignInScreen.name)
                        }
                    )
                }
                Spacer(modifier = Modifier.height(30.dp))

            }


        }


    }

}