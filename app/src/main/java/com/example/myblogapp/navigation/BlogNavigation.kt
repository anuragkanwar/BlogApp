package com.example.myblogapp.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myblogapp.screens.blog.BlogScreen
import com.example.myblogapp.screens.blog.BlogScreenViewModel
import com.example.myblogapp.screens.blogAddEditScreen.BlogAddEditScreen
import com.example.myblogapp.screens.blogAddEditScreen.BlogAddEditViewModel
import com.example.myblogapp.screens.commentScreen.CommentScreen
import com.example.myblogapp.screens.commentScreen.CommentScreenViewModel
import com.example.myblogapp.screens.entryScreen.EntryScreen
import com.example.myblogapp.screens.entryScreen.EntryScreenViewModel
import com.example.myblogapp.screens.homescreen.HomeScreen
import com.example.myblogapp.screens.homescreen.HomeScreenViewModel
import com.example.myblogapp.screens.login.LoginScreenViewModel
import com.example.myblogapp.screens.login.SignInScreen
import com.example.myblogapp.screens.login.SignUpScreen
import com.example.myblogapp.screens.profileScreen.ProfileScreen
import com.example.myblogapp.screens.profileScreen.ProfileScreenViewModel
import com.example.myblogapp.screens.splashscreen.SplashScreen
import com.example.myblogapp.screens.splashscreen.SplashScreenViewModel

@Composable
fun BlogNavigation() {

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = BlogScreens.SplashScreen.name) {


        composable(route = BlogScreens.SplashScreen.name) {
            val viewModel: SplashScreenViewModel = hiltViewModel()
            SplashScreen(navController = navController, viewModel = viewModel)
        }

        composable(route = BlogScreens.HomeScreen.name) {
            val viewModel: HomeScreenViewModel = hiltViewModel()
            HomeScreen(navController = navController, viewModel = viewModel)
        }

        composable(route = BlogScreens.EntryScreen.name) {
            val viewModel: EntryScreenViewModel = hiltViewModel()
            EntryScreen(navController = navController, viewModel = viewModel)
        }

        composable(route = BlogScreens.SignUpScreen.name) {
            val viewModel: LoginScreenViewModel = hiltViewModel()
            SignUpScreen(navController = navController, viewModel = viewModel)
        }
        composable(route = BlogScreens.SignInScreen.name) {
            val viewModel: LoginScreenViewModel = hiltViewModel()
            SignInScreen(navController = navController, viewModel = viewModel)
        }

        composable(
            route = BlogScreens.BlogScreen.name + "/{id}",
            arguments = listOf(navArgument(name = "id") { type = NavType.IntType })
        ) { backstackEntry ->
            val viewModel: BlogScreenViewModel = hiltViewModel()
            BlogScreen(
                navController = navController,
                viewModel = viewModel,
                backstackEntry.arguments?.getInt("id")
            )
        }

        composable(route = BlogScreens.CommentScreen.name + "/{id}",arguments = listOf(navArgument(name = "id") { type = NavType.IntType })
        ) { backstackEntry ->
            val viewModel: CommentScreenViewModel = hiltViewModel()
            CommentScreen(
                navController = navController,
                viewModel = viewModel,
                backstackEntry.arguments?.getInt("id")
            )
        }

        composable(route = BlogScreens.ProfileScreen.name){
            val viewModel: ProfileScreenViewModel = hiltViewModel()
            ProfileScreen(navController = navController, viewModel = viewModel)
        }

        composable(route = BlogScreens.AddEditBlogScreen.name){
            val viewModel : BlogAddEditViewModel = hiltViewModel()
            BlogAddEditScreen(navController = navController, viewModel = viewModel)
        }

    }
}