package com.example.geminiapimodelstesting.text

import androidx.lifecycle.ViewModel
import com.example.geminiapimodelstesting.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TextViewModel : ViewModel() {

    private val _uiState: MutableStateFlow<UiState> =
        MutableStateFlow(UiState.Initial)
    val uiState: StateFlow<UiState> =
        _uiState.asStateFlow()

    fun generateStory(inputText: String) {
        _uiState.value = UiState.Loading

    }
}
