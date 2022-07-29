package com.example.myblogapp.screens.profileScreen

import androidx.lifecycle.ViewModel
import com.example.myblogapp.repository.BlogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val repository: BlogRepository
) : ViewModel(){

}