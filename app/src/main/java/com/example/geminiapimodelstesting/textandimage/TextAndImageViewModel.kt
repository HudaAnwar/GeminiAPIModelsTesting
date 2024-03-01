package com.example.geminiapimodelstesting.textandimage

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.geminiapimodelstesting.UiState
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TextAndImageViewModel(
    private val generativeModel: GenerativeModel
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState> =
        MutableStateFlow(UiState.Initial)
    val uiState: StateFlow<UiState> =
        _uiState.asStateFlow()

    fun submitImageAndText(inputText: String, imageBitmap: Bitmap) {
        _uiState.value = UiState.Loading

        val promptContent = content {
            image(imageBitmap)
            text(inputText)
        }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = generativeModel.generateContent(promptContent)
                response.text?.let { output ->
                    _uiState.value = UiState.Success(output)
                }
            } catch (ex: Exception) {
                _uiState.value = UiState.Error(ex.localizedMessage ?: "Unknown Error")
            }
        }
    }
}
