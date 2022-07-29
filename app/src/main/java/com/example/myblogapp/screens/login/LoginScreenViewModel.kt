package com.example.myblogapp.screens.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.focus.FocusState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myblogapp.model.respond.LoginUser
import com.example.myblogapp.model.respond.RegisterUser
import com.example.myblogapp.repository.BlogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val repository: BlogRepository
) : ViewModel() {

    private val _userEmail = mutableStateOf(
        BlogTextFieldState(
            hintText = "Enter Email..."
        )
    )
    val userEmail: State<BlogTextFieldState> = _userEmail

    private val _userName = mutableStateOf(
        BlogTextFieldState(
            hintText = "Enter Full Name..."
        )
    )
    val userName: State<BlogTextFieldState> = _userName

    private val _userPassword = mutableStateOf(
        BlogPasswordTextFieldState(
            hintText = "Enter Password..."
        )
    )
    val userPassword: State<BlogPasswordTextFieldState> = _userPassword

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    fun onEvent(event: UserEvents) {
        when (event) {
            is UserEvents.EnteredEmail -> {
                _userEmail.value = _userEmail.value.copy(
                    text = event.value
                )
            }
            is UserEvents.EnteredName -> {
                _userName.value = _userName.value.copy(
                    text = event.value
                )
            }
            is UserEvents.EnteredPassword -> {
                _userPassword.value = _userPassword.value.copy(
                    text = event.value
                )
            }
            is UserEvents.ChangeEmailFocus -> {
                _userEmail.value = _userEmail.value.copy(
                    isHintVisible = !event.focusState.isFocused && userEmail.value.text.isBlank()
                )
            }
            is UserEvents.ChangeNameFocus -> {
                _userName.value = _userName.value.copy(
                    isHintVisible = !event.focusState.isFocused && userName.value.text.isBlank()
                )
            }
            is UserEvents.ChangePasswordFocus -> {
                _userPassword.value = _userPassword.value.copy(
                    isHintVisible = !event.focusState.isFocused && userPassword.value.text.isBlank()
                )
            }
            is UserEvents.ChangePasswordVisibility -> {
                _userPassword.value = _userPassword.value.copy(
                    passwordVisible = !userPassword.value.passwordVisible
                )
            }
            is UserEvents.Register -> {
                viewModelScope.launch {
                    try {
                        _eventFlow.emit(UiEvent.Loading)
                        val response = repository.registerUser(
                            RegisterUser(
                                email = userEmail.value.text.trim(),
                                firstName = userName.value.text.trim().split(' ')[0],
                                lastLogin = System.currentTimeMillis(),
                                lastName = userName.value.text.trim().split(' ')[1],
                                middleName = "",
                                mobile = "",
                                password = userPassword.value.text,
                                registerAt = System.currentTimeMillis()
                            )
                        )
                        if (response.data == null) {
                            _eventFlow.emit(
                                UiEvent.ShowSnackbar(
                                    message = response.e!!.toString()
                                )
                            )
                        } else {
                            _eventFlow.emit(UiEvent.Success)
                        }
                    } catch (e: Exception) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Couldn't register"
                            )
                        )
                    }
                }
            }
            is UserEvents.Login -> {
                viewModelScope.launch {
                    try {
                        _eventFlow.emit(UiEvent.Loading)

                        val response = repository.loginUser(
                            LoginUser(
                                email = userEmail.value.text.trim(),
                                password = userPassword.value.text,
                            )
                        )
                        if (response.data == null) {
                            _eventFlow.emit(
                                UiEvent.ShowSnackbar(
                                    message = response.e!!.toString()
                                )
                            )
                        } else {
                            _eventFlow.emit(UiEvent.Success)
                        }
                    } catch (e: Exception) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Couldn't register"
                            )
                        )
                    }
                }
            }
        }
    }


    sealed class UserEvents {
        data class EnteredEmail(val value: String) : UserEvents()
        data class EnteredName(val value: String) : UserEvents()
        data class EnteredPassword(val value: String) : UserEvents()

        data class ChangeEmailFocus(val focusState: FocusState) : UserEvents()
        data class ChangeNameFocus(val focusState: FocusState) : UserEvents()
        data class ChangePasswordFocus(val focusState: FocusState) : UserEvents()

        object ChangePasswordVisibility : UserEvents()

        object Register : UserEvents()
        object Login : UserEvents()

    }


    sealed class UiEvent {
        object Loading : UiEvent()
        data class ShowSnackbar(val message: String) : UiEvent()
        object Success : UiEvent()
    }

}