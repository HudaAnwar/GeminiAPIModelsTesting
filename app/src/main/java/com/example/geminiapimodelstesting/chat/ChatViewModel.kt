package com.example.geminiapimodelstesting.chat

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.geminiapimodelstesting.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ChatViewModel : ViewModel() {

    private val _chatUiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Initial)
    val chatUiState = _chatUiState.asStateFlow()

    val messages = mutableStateOf(emptyList<Message>())
    val userInput = mutableStateOf("")

    fun sendMessage(message: String) {

    }

    private fun onUserInputChange(text: String) {
        userInput.value = text
    }

    private fun startChat(initialMessages: List<Any>) {

    }
    fun getMessages(): List<Message> {
        return messages.value
    }
}
