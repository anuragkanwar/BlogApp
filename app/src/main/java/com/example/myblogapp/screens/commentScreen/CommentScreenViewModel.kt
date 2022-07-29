package com.example.myblogapp.screens.commentScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myblogapp.model.respond.AddComment
import com.example.myblogapp.model.respond.DeleteComment
import com.example.myblogapp.model.response.Comment
import com.example.myblogapp.model.response.CommentWithUser
import com.example.myblogapp.repository.BlogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentScreenViewModel @Inject constructor(
    private val repository: BlogRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    val id = savedStateHandle.get<Int>("id")!!
    var comment = mutableStateOf<MutableList<CommentWithUser>>(mutableListOf())

    init {
        getAllComments(id)
    }


    fun getAllComments(blogId: Int) {
        isLoading.value = true
        viewModelScope.launch {
            val result = repository.getAllComments(blogId)
            if (result.data == null) {
                loadError.value = result.e.toString()
                isLoading.value = false
            } else {
                comment.value = result.data!!.toMutableList()
                isLoading.value = false
                loadError.value = ""
            }
        }
    }

    fun addComment(
        content: String
    ) {
        isLoading.value = true
        viewModelScope.launch {
            val result = repository.addComment(
                AddComment(
                    blogId = id,
                    content = content,
                    likes = 0,
                    pusblishedAt = System.currentTimeMillis()
                )
            )
            if (result.data == null) {
                loadError.value = result.e.toString()
                isLoading.value = false
            } else {
                comment.value.add(result.data!!)
                isLoading.value = false
                loadError.value = ""
            }
        }
    }

    fun removeComment(
        commentId: Int
    ) {
        isLoading.value = true
        viewModelScope.launch {
            val result = repository.deleteComment(
                DeleteComment(
                    blogId = id,
                    id = commentId
                )
            )
            if (result.data == null) {
                loadError.value = result.e.toString()
                isLoading.value = false
            } else {
                comment.value.removeIf {
                    it.id == commentId
                }
                isLoading.value = false
                loadError.value = ""
            }
        }
    }
}