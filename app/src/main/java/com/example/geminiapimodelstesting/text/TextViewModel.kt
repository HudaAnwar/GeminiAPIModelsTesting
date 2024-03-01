package com.example.geminiapimodelstesting.text

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.geminiapimodelstesting.UiState
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TextViewModel(
    private val generativeModel: GenerativeModel
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState> =
        MutableStateFlow(UiState.Initial)
    val uiState: StateFlow<UiState> =
        _uiState.asStateFlow()

    fun generateStory(inputText: String) {
        _uiState.value = UiState.Loading
        val prompt = "Generate a short story with the following description: $inputText"
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = generativeModel.generateContent(prompt)
                response.text?.let { story ->
                    _uiState.value = UiState.Success(story)
                }
            } catch (ex: Exception) {
                _uiState.value = UiState.Error(ex.localizedMessage ?: "Unknown Error")
            }
        }
    }
}
