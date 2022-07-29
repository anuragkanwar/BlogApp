package com.example.myblogapp.screens.entryScreen

import androidx.lifecycle.ViewModel
import com.example.myblogapp.repository.BlogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EntryScreenViewModel @Inject constructor(
    private val repository: BlogRepository
) : ViewModel() {
}