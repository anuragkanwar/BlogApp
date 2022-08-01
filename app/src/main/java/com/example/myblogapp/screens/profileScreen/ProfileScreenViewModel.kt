package com.example.myblogapp.screens.profileScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myblogapp.repository.BlogRepository
import com.example.myblogapp.screens.login.LoginScreenViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val repository: BlogRepository
) : ViewModel(){

    var isLoading = mutableStateOf(false)

    var isSuccess = mutableStateOf(false)

    var loadError = mutableStateOf("")


    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun logout() {
        viewModelScope.launch {
            isLoading.value = true
            val result = repository.logout()
            if (result.data == null) {
                isLoading.value = false
                _eventFlow.emit(UiEvent.ShowSnackbar("Cant log out"))
            } else {
                isLoading.value = false
                _eventFlow.emit(UiEvent.Success)
            }
        }
    }

    sealed class UiEvent {
        object Loading : UiEvent()
        data class ShowSnackbar(val message: String) : UiEvent()
        object Success : UiEvent()
    }


    suspend fun getUserImage() :  String{
        var imageUrl = ""
        viewModelScope.launch {
            imageUrl = repository.sessionManager.getCurrentImageUrl().toString()
        }.join()
        return imageUrl
    }

    suspend fun getUserName() :  String{
        var fname = ""
        viewModelScope.launch {
            fname = repository.sessionManager.getCurrentUserName().toString()
        }.join()
        return fname
    }
}