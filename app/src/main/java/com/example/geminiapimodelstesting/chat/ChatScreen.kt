package com.example.geminiapimodelstesting.chat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.geminiapimodelstesting.UiState

@Composable
fun ChatRoute(viewModel: ChatViewModel = viewModel()) {
    val chatUiState by viewModel.chatUiState.collectAsState()

    ChatScreen(
        chatUiState,
        onSendMessage = { message ->
            viewModel.sendMessage(message)
        },
        onGetMessages = {
            viewModel.getMessages()
        }
    )
}

@Composable
fun ChatScreen(
    uiState: UiState = UiState.Initial,
    onSendMessage: (String) -> Unit = {},
    onGetMessages: () -> List<Message> = { emptyList<Message>() }
) {
    var prompt by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .padding(all = 8.dp)
            .height(680.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        val messageListScrollState = rememberScrollState()
        MessageList(
            messages = onGetMessages(),
            modifier = Modifier
                .weight(1f)
                .verticalScroll(messageListScrollState) // Use dedicated scroll state
        )
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
            Row(modifier = Modifier.fillMaxWidth()) {
                TextField(
                    value = prompt, // Bind to prompt state
                    onValueChange = { prompt = it }, // Update prompt on changes
                    modifier = Modifier
                        .weight(8f)
                        .padding(end = 8.dp)
                )
                Button(
                    onClick = {
                        if (prompt.isNotBlank()) {
                            onSendMessage(prompt) // Call sendMessage directly
                            prompt = "" // Clear input after sending
                        }
                    },
                    modifier = Modifier
                        .weight(3f)
                ) {
                    Text("Send")
                }
            }
        }


        when (uiState) {
            UiState.Initial -> {
                // Nothing to show
            }

            UiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            is UiState.Success -> {
                // Success state handled implicitly by updating messages
            }

            is UiState.Error -> {
                Text(
                    text = uiState.errorMessage,
                    color = Color.Red,
                    modifier = Modifier.padding(all = 8.dp)
                )
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun ChatScreenPreview() {
    ChatScreen()
}