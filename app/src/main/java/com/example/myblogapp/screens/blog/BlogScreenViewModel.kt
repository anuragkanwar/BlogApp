package com.example.myblogapp.screens.blog

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myblogapp.model.response.FullBlog
import com.example.myblogapp.repository.BlogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BlogScreenViewModel @Inject constructor(
    private val repository: BlogRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var blog = mutableStateOf<FullBlog?>(null)

    init {
        isLoading.value = true
        savedStateHandle.get<Int>("id")?.let { blogId ->
            viewModelScope.launch {
                val result = repository.getSingleBlogs(blogId.toString())
                if (result.data == null) {
                    loadError.value = result.e.toString()
                    isLoading.value = false
                } else {
                    blog.value = result.data
                    isLoading.value = false
                    loadError.value = ""
                }
            }
        }
    }

}