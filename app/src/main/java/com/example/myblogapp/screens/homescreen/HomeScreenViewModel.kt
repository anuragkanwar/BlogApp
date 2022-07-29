package com.example.myblogapp.screens.homescreen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myblogapp.model.response.MinBlog
import com.example.myblogapp.repository.BlogRepository
import com.example.myblogapp.screens.login.LoginScreenViewModel
import com.example.myblogapp.utils.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repository: BlogRepository
) : ViewModel() {

    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var blogList = mutableStateOf<List<MinBlog>>(listOf())
    var imageUrl = ""


    init {
        loadBlogs()
    }


    fun loadBlogs(
        category: String = ""
    ) {
        if (category.isEmpty()) {
            viewModelScope.launch {
                isLoading.value = true
                val result = repository.getAllBlogs()
                if (result.data == null) {
                    loadError.value = result.e.toString()
                    isLoading.value = false
                } else {
                    blogList.value = result.data!!
                    isLoading.value = false
                    loadError.value = ""
                }
            }
        } else {
            viewModelScope.launch {
                isLoading.value = true
                val result = repository.getAllBlogsByFilter(category)
                if (result.data == null) {
                    loadError.value = result.e.toString()
                    isLoading.value = false
                } else {
                    blogList.value = result.data!!
                    isLoading.value = false
                    loadError.value = ""
                }
            }
        }
    }

    suspend fun getImage(): String {
        val imgeurl = ""
        viewModelScope.launch {
            imageUrl = repository.sessionManager.getCurrentImageUrl().toString()
        }.join()
        return imageUrl
    }

}
