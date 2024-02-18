package com.example.geminiapimodelstesting.textandimage

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.example.geminiapimodelstesting.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TextAndImageViewModel : ViewModel() {

    private val _uiState: MutableStateFlow<UiState> =
        MutableStateFlow(UiState.Initial)
    val uiState: StateFlow<UiState> =
        _uiState.asStateFlow()

    fun submitImageAndText(inputText: String, imageBitmap: Bitmap) {
        _uiState.value = UiState.Loading

    }
}
