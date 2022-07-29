package com.example.myblogapp.screens.splashscreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myblogapp.repository.BlogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val repository: BlogRepository
) : ViewModel() {

    suspend fun checkUser(): Boolean {
        var ok = true
        viewModelScope.launch {
            ok = repository.isLoggedIn()
        }.join()
        return ok
    }

}