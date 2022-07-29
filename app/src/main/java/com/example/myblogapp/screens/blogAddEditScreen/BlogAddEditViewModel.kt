package com.example.myblogapp.screens.blogAddEditScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.focus.FocusState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myblogapp.model.respond.CreateBlog
import com.example.myblogapp.model.respond.LoginUser
import com.example.myblogapp.model.respond.RegisterUser
import com.example.myblogapp.model.response.User
import com.example.myblogapp.repository.BlogRepository
import com.example.myblogapp.screens.login.BlogTextFieldState
import com.example.myblogapp.screens.login.LoginScreenViewModel
import com.example.myblogapp.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BlogAddEditViewModel @Inject constructor(
    private val repository: BlogRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _blogTitle = mutableStateOf(
        BlogTextFieldState(
            hintText = "Enter Title..."
        )
    )
    val blogTitle: State<BlogTextFieldState> = _blogTitle

    private val _blogContent = mutableStateOf(
        BlogTextFieldState(
            hintText = "Enter Content..."
        )
    )
    val blogContent: State<BlogTextFieldState> = _blogContent

    private val _blogCategory = mutableStateOf(
        Constants.categories.first()
    )
    val blogCategory: State<String> = _blogCategory

    private val _blogSummary = mutableStateOf(
        BlogTextFieldState(
            hintText = "Enter Summary..."
        )
    )
    val blogSummary: State<BlogTextFieldState> = _blogSummary

    private val _blogMetaTitle = mutableStateOf(
        BlogTextFieldState(
            hintText = "Enter MetaTitle..."
        )
    )
    val blogMetaTitle: State<BlogTextFieldState> = _blogMetaTitle

    private val _eventFlow = MutableSharedFlow<BlogScreenUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: BlogEvents) {
        when (event) {
            is BlogEvents.EnteredTitle -> {
                _blogTitle.value = _blogTitle.value.copy(
                    text = event.value
                )
            }
            is BlogEvents.EnteredContent -> {
                _blogContent.value = _blogContent.value.copy(
                    text = event.value
                )
            }
            is BlogEvents.EnteredMetaTitle -> {
                _blogMetaTitle.value = _blogMetaTitle.value.copy(
                    text = event.value
                )
            }
            is BlogEvents.EnteredSummary -> {
                _blogSummary.value = _blogSummary.value.copy(
                    text = event.value
                )
            }

            is BlogEvents.ChangeCategory -> {
                _blogCategory.value = event.value
            }

            is BlogEvents.ChangeEnteredContentFocus -> {
                _blogContent.value = _blogContent.value.copy(
                    isHintVisible = !event.focusState.isFocused && blogContent.value.text.isBlank()
                )
            }

            is BlogEvents.ChangeEnteredMetaTitleFocus -> {
                _blogMetaTitle.value = _blogMetaTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused && blogMetaTitle.value.text.isBlank()
                )
            }

            is BlogEvents.ChangeEnteredSummaryFocus -> {
                _blogSummary.value = _blogSummary.value.copy(
                    isHintVisible = !event.focusState.isFocused && blogSummary.value.text.isBlank()
                )
            }

            is BlogEvents.ChangeEnteredTitleFocus -> {
                _blogTitle.value = _blogTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused && blogTitle.value.text.isBlank()
                )
            }

            is BlogEvents.Publish -> {
                viewModelScope.launch {
                    try {
                        _eventFlow.emit(
                            BlogScreenUiEvent.Loading
                        )
                        val result = repository.publishBlog(
                            CreateBlog(
                                category = blogCategory.value,
                                content = blogContent.value.text,
                                createdAt = System.currentTimeMillis(),
                                imgUrl = "https://placeholder.jpg",
                                metaTitle = blogMetaTitle.value.text,
                                published = true,
                                publishedAt = System.currentTimeMillis(),
                                summary = blogSummary.value.text,
                                title = blogSummary.value.text,
                                updatedAt = System.currentTimeMillis()
                            )
                        )
                        if (result.data != null) {
                            _eventFlow.emit(
                                BlogScreenUiEvent.Success
                            )
                        } else {
                            _eventFlow.emit(
                                BlogScreenUiEvent.ShowSnackbar(
                                    message = result.e?.message ?: "Cant Publish"
                                )
                            )
                        }
                    } catch (e: Exception) {
                        _eventFlow.emit(
                            BlogScreenUiEvent.ShowSnackbar(
                                message = e.message ?: "Cant Publish"
                            )
                        )
                    }
                }
            }

            is BlogEvents.Save -> {
                //TODO
            }
        }

    }


    sealed class BlogEvents {
        data class EnteredTitle(val value: String) : BlogEvents()
        data class EnteredContent(val value: String) : BlogEvents()
        data class EnteredSummary(val value: String) : BlogEvents()
        data class EnteredMetaTitle(val value: String) : BlogEvents()
        data class ChangeCategory(val value: String) : BlogEvents()


        data class ChangeEnteredTitleFocus(val focusState: FocusState) : BlogEvents()
        data class ChangeEnteredContentFocus(val focusState: FocusState) : BlogEvents()
        data class ChangeEnteredSummaryFocus(val focusState: FocusState) : BlogEvents()
        data class ChangeEnteredMetaTitleFocus(val focusState: FocusState) : BlogEvents()

        object Publish : BlogEvents()
        object Save : BlogEvents()
    }

    sealed class BlogScreenUiEvent {
        object Loading : BlogScreenUiEvent()
        data class ShowSnackbar(val message: String) : BlogScreenUiEvent()
        object Success : BlogScreenUiEvent()
    }

}