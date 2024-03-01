package com.example.geminiapimodelstesting.chat

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.geminiapimodelstesting.UiState
import com.google.ai.client.generativeai.Chat
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.Content
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChatViewModel(
    private val generativeModel: GenerativeModel
) : ViewModel() {

    private val _chatUiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Initial)
    val chatUiState = _chatUiState.asStateFlow()

    val messages = mutableStateOf(emptyList<Message>())
    val userInput = mutableStateOf("")

    private var chat: Chat? = null

    fun sendMessage(message: String) {
        if (chat == null) {
            startChat(
                listOf(
                    content(role = "user") { text("Hello! I am a data scientist and I live in Croatia.") },
                    content(role = "model") { text("Hi user, What do you want to know?") }
                )
            )
        }
        _chatUiState.value = UiState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = chat?.sendMessage(message)

                messages.value += listOf<Message>(Message.UserMessage(message))
                messages.value += listOf<Message>(Message.GeminiMessage(response?.text.toString()))

                _chatUiState.value = UiState.Success()

            } catch (ex: Exception) {
                _chatUiState.value = UiState.Error(ex.localizedMessage ?: "Unknown Error")
            }
        }

    }

    private fun onUserInputChange(text: String) {
        userInput.value = text
    }

    private fun startChat(initialMessages: List<Content>) {
        chat = generativeModel.startChat(initialMessages)
    }

    fun getMessages(): List<Message> {
        return messages.value
    }
}
