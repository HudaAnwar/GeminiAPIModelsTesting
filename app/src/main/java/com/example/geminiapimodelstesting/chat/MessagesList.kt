package com.example.geminiapimodelstesting.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MessageList(messages: List<Message>, modifier: Modifier) {
    Column(modifier = modifier ) {
        messages.forEach { message ->
            when (message) {
                is Message.UserMessage -> UserMessage(message.text)
                is Message.GeminiMessage -> GeminiMessage(message.text)
            }
        }
    }
}

@Composable
fun UserMessage(text: String) {
    Row(modifier = Modifier.padding(4.dp)) {
        // Use a custom avatar for User Messages (optional)
        Image(imageVector = Icons.Default.AccountCircle, contentDescription = "User")
        Spacer(modifier = Modifier.width(8.dp))
        Text(text, color = MaterialTheme.colorScheme.tertiary)
    }
}

@Composable
fun GeminiMessage(text: String) {
    Row(modifier = Modifier.padding(4.dp)) {
        // Use a different avatar for Gemini Messages (optional)
        Image(imageVector = Icons.Default.MailOutline, contentDescription = "Gemini")
        Spacer(modifier = Modifier.width(8.dp))
        Text(text, color = MaterialTheme.colorScheme.primary)
    }
}

