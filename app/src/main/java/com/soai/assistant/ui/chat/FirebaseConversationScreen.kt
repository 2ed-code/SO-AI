package com.soai.assistant.ui.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.soai.assistant.data.model.ChatMessage
import com.soai.assistant.data.repository.ChatRepository
import kotlinx.coroutines.launch

@Composable
fun FirebaseConversationScreen(conversationId: String, title: String, onBack: () -> Unit) {
    val user = FirebaseAuth.getInstance().currentUser
    val repository = remember { ChatRepository() }
    val scope = rememberCoroutineScope()
    var messages by remember { mutableStateOf(emptyList<ChatMessage>()) }
    var draft by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    DisposableEffect(conversationId) {
        val registration = repository.observeMessages(
            conversationId,
            { messages = it },
            { error = it.localizedMessage ?: "Could not load messages." }
        )
        onDispose { registration.remove() }
    }

    Column(Modifier.fillMaxSize().background(Color(0xFF09090D)).padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            TextButton(onClick = onBack) { Text("‹ Back", color = Color(0xFFA78BFA)) }
            Text(title, color = Color.White, fontSize = 19.sp, fontWeight = FontWeight.SemiBold)
        }
        error?.let { Text(it, color = Color(0xFFFF7272)) }
        LazyColumn(Modifier.weight(1f).fillMaxWidth(), reverseLayout = false, contentPadding = PaddingValues(vertical = 12.dp)) {
            items(messages, key = { it.id }) { message ->
                Row(Modifier.fillMaxWidth(), horizontalArrangement = if (message.senderId == user?.uid) Arrangement.End else Arrangement.Start) {
                    Surface(color = if (message.senderId == user?.uid) Color(0xFF4F3EC9) else Color(0xFF20202A), shape = RoundedCornerShape(16.dp)) {
                        Text(message.text, color = Color.White, modifier = Modifier.padding(horizontal = 12.dp, vertical = 9.dp))
                    }
                }
                Spacer(Modifier.height(8.dp))
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = draft, onValueChange = { draft = it }, modifier = Modifier.weight(1f),
                placeholder = { Text("Message…") }, singleLine = true
            )
            Button(onClick = {
                val text = draft
                val uid = user?.uid
                if (uid != null && text.isNotBlank()) scope.launch {
                    try {
                        repository.sendMessage(conversationId, uid, text)
                        draft = ""
                        error = null
                    } catch (e: Exception) { error = e.localizedMessage ?: "Message could not be sent." }
                }
            }) { Text("Send") }
        }
    }
}
