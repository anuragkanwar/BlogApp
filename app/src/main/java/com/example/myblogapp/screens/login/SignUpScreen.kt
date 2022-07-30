package com.example.myblogapp.screens.login

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.myblogapp.components.LottieComponent
import com.example.myblogapp.components.MyButton
import com.example.myblogapp.components.TransparentHintTextField
import com.example.myblogapp.navigation.BlogScreens
import com.example.myblogapp.utils.AppColors
import kotlinx.coroutines.flow.collectLatest
import java.util.regex.Pattern

@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel: LoginScreenViewModel
) {
    val emailState = viewModel.userEmail.value
    val nameState = viewModel.userName.value
    val passwordState = viewModel.userPassword.value

    val scaffoldState = rememberScaffoldState()

    val context = LocalContext.current

    val isLoading = remember {
        mutableStateOf(false)
    }


    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is LoginScreenViewModel.UiEvent.Loading -> {
                    isLoading.value = true
                }
                is LoginScreenViewModel.UiEvent.ShowSnackbar -> {
                    isLoading.value = false
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message + "Try again Later"
                    )
                }
                is LoginScreenViewModel.UiEvent.Success -> {
                    isLoading.value = false
                    navController.navigate(BlogScreens.HomeScreen.name) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = {
            SnackbarHost(it) { data ->
                Snackbar(
                    actionColor = Color.White,
                    snackbarData = data,
                    backgroundColor = Color.Black,
                    contentColor = Color.White
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(it)
                .padding(top = 35.dp,start = 8.dp,end = 8.dp,bottom = 8.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top
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

            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Let's get started!",
                fontSize = 28.sp,
                color = AppColors.black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 5.dp)
            )

            Text(
                text = "Enter Your Information Below...",
                fontSize = 22.sp,
                color = Color.DarkGray,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 5.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "E-mail",
                color = Color.LightGray,
                fontSize = 20.sp,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 3.dp)
            )
            TransparentHintTextField(
                text = emailState.text,
                hint = emailState.hintText,
                onValueChange = { viewModel.onEvent(LoginScreenViewModel.UserEvents.EnteredEmail(it)) },
                onFocusChange = {
                    viewModel.onEvent(
                        LoginScreenViewModel.UserEvents.ChangeEmailFocus(
                            it
                        )
                    )
                },
                isHintVisible = emailState.isHintVisible,
                singleLine = true,
                textStyle = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = AppColors.black
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


            Text(
                text = "Full Name",
                color = Color.LightGray,
                fontSize = 20.sp,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 3.dp)
            )
            TransparentHintTextField(
                text = nameState.text,
                hint = nameState.hintText,
                onValueChange = { viewModel.onEvent(LoginScreenViewModel.UserEvents.EnteredName(it)) },
                onFocusChange = {
                    viewModel.onEvent(
                        LoginScreenViewModel.UserEvents.ChangeNameFocus(
                            it
                        )
                    )
                },
                isHintVisible = nameState.isHintVisible,
                singleLine = true,
                textStyle = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = AppColors.black
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

            Text(
                text = "Password",
                color = Color.LightGray,
                fontSize = 20.sp,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 3.dp)
            )
            TransparentHintTextField(
                text = passwordState.text,
                hint = passwordState.hintText,
                onValueChange = {
                    viewModel.onEvent(
                        LoginScreenViewModel.UserEvents.EnteredPassword(
                            it
                        )
                    )
                },
                onFocusChange = {
                    viewModel.onEvent(
                        LoginScreenViewModel.UserEvents.ChangePasswordFocus(
                            it
                        )
                    )
                },
                onPasswordChange = { viewModel.onEvent(LoginScreenViewModel.UserEvents.ChangePasswordVisibility) },
                isPasswordField = true,
                isPasswordVisible = passwordState.passwordVisible,
                isHintVisible = passwordState.isHintVisible,
                singleLine = true,
                textStyle = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = AppColors.black
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

            MyButton(
                modifier = Modifier
                    .padding(horizontal = 30.dp, vertical = 12.dp)
                    .fillMaxWidth(),
                text = "Sign up",
                onClick = {
                    if (nameState.text.isNotEmpty() and validateEmail(emailState.text) and validatePassword(
                            passwordState.text
                        )
                    ) {

                        viewModel.onEvent(LoginScreenViewModel.UserEvents.Register)

                    } else {
                        Toast.makeText(context, "Check your fields...", Toast.LENGTH_SHORT).show()
                    }
                }
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = AppColors.black,
                                fontSize = 20.sp,
                            )
                        ) {
                            append("Already have an account?")
                        }
                        withStyle(
                            style = SpanStyle(
                                color = AppColors.black,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append("Log in!")
                        }
                    },
                    modifier = Modifier.clickable {
                        navController.navigate(BlogScreens.SignInScreen.name)
                    }
                )
            }
        }

        if (isLoading.value) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background),
                contentAlignment = Alignment.Center
            ) {
                LoadingIndicator()
            }
        }

    }
}


private fun validateEmail(email: String): Boolean {
    val regex = "^[a-zA-Z0-9_&*-]+(?:\\.[a-zA-z0-9_&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
    val pattern = Pattern.compile(regex)
    return (email.isNotEmpty() && pattern.matcher(email).matches())
}

private fun validatePassword(password: String): Boolean {
    val regex = "(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[^A-Za-z0-9]).{8,20}"
    val pattern = Pattern.compile(regex)
    return (password.isNotEmpty() && pattern.matcher(password).matches())

}

