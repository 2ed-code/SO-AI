package com.soai.assistant.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val AppBackground = Color(0xFF09090D)
private val SurfaceDark = Color(0xFF17171F)
private val Accent = Color(0xFF6A5CFF)
private val Muted = Color(0xFF9A9AA8)

private data class ChatPreview(
    val name: String,
    val message: String,
    val time: String,
    val unread: Int = 0,
    val online: Boolean = false,
    val avatar: String = "●"
)

private val sampleChats = listOf(
    ChatPreview("Lora", "See you tonight ❤️", "12:45 AM", 2, true, "L"),
    ChatPreview("Ahmed", "Sent a photo 📷", "Yesterday", 1, true, "A"),
    ChatPreview("Sarah", "Typing…", "10:20 PM", 0, true, "S"),
    ChatPreview("Dev Team", "Last update was awesome!", "Mon", 0, false, "D"),
    ChatPreview("Omar", "Let's catch up soon", "Sun", 0, false, "O"),
    ChatPreview("Mohamed", "See you later", "Sat", 0, false, "M")
)

@Composable
fun HomeScreen() {
    var selectedTab by remember { mutableStateOf("Messages") }
    var search by remember { mutableStateOf("") }
    var draft by remember { mutableStateOf("") }
    var openedChat by remember { mutableStateOf<ChatPreview?>(null) }
    var showStatusMenu by remember { mutableStateOf(false) }
    var status by remember { mutableStateOf("Online") }

    Surface(modifier = Modifier.fillMaxSize(), color = AppBackground) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize().padding(horizontal = 18.dp, vertical = 18.dp)
            ) {
                if (openedChat == null) {
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("SO Chat", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                            Text(
                                when (selectedTab) {
                                    "Friends" -> "People you know"
                                    "Groups" -> "Your group conversations"
                                    "Calls" -> "Recent and missed calls"
                                    "Notifications" -> "Your latest activity"
                                    "Profile" -> "Your account and status"
                                    else -> "Stay close to your people"
                                },
                                color = Muted, fontSize = 13.sp
                            )
                        }
                        Box {
                            Text(
                                "●  $status  ▾",
                                color = if (status == "Online") Color(0xFF55D66B) else Muted,
                                modifier = Modifier.clip(RoundedCornerShape(24.dp))
                                    .background(SurfaceDark).clickable { showStatusMenu = true }
                                    .padding(horizontal = 12.dp, vertical = 10.dp),
                                fontSize = 12.sp
                            )
                            DropdownMenu(expanded = showStatusMenu, onDismissRequest = { showStatusMenu = false }) {
                                listOf("Online", "Offline", "Do Not Disturb").forEach { item ->
                                    DropdownMenuItem(text = { Text(item) }, onClick = {
                                        status = item
                                        showStatusMenu = false
                                    })
                                }
                            }
                        }
                    }
                    Spacer(Modifier.height(22.dp))
                    when (selectedTab) {
                        "Profile" -> {
                            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth().padding(top = 28.dp)) {
                                Avatar("S", status == "Online", 92)
                                Spacer(Modifier.height(14.dp))
                                Text("Your profile", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                                Text("@yourusername", color = Muted)
                                Spacer(Modifier.height(20.dp))
                                Text("Edit profile and privacy settings will be available here.", color = Muted)
                            }
                        }
                        "Calls" -> SimpleStateList("Calls", listOf("No calls yet"))
                        "Notifications" -> SimpleStateList("Notifications", listOf("Friend requests", "Mentions", "Messages", "System alerts"))
                        else -> {
                            OutlinedTextField(
                                value = search, onValueChange = { search = it },
                                modifier = Modifier.fillMaxWidth(),
                                placeholder = { Text("Search messages and friends", color = Muted) },
                                leadingIcon = { Text("⌕", color = Muted, fontSize = 23.sp) },
                                singleLine = true,
                                shape = RoundedCornerShape(18.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = Color.White, unfocusedTextColor = Color.White,
                                    focusedBorderColor = Accent, unfocusedBorderColor = Color(0xFF30303A),
                                    cursorColor = Accent
                                )
                            )
                            Spacer(Modifier.height(18.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                listOf("Messages", "Friends", "Groups").forEach { tab ->
                                    FilterChip(
                                        selected = selectedTab == tab,
                                        onClick = { selectedTab = tab },
                                        label = { Text(tab) },
                                        colors = FilterChipDefaults.filterChipColors(
                                            selectedContainerColor = Accent,
                                            selectedLabelColor = Color.White,
                                            labelColor = Muted
                                        )
                                    )
                                }
                                Spacer(Modifier.weight(1f))
                                FilledTonalButton(onClick = { selectedTab = "New chat" }) { Text("+") }
                            }
                            Spacer(Modifier.height(8.dp))
                            when (selectedTab) {
                                "Friends" -> SimpleStateList("Friends", listOf("Lora", "Ahmed", "Sarah", "Omar"))
                                "Groups" -> SimpleStateList("Groups", listOf("Dev Team", "Weekend plans", "Family"))
                                "New chat" -> SimpleStateList("Start a conversation", listOf("Lora", "Ahmed", "Sarah", "Omar"))
                                else -> {
                                    val filtered = sampleChats.filter {
                                        it.name.contains(search, ignoreCase = true) || it.message.contains(search, ignoreCase = true)
                                    }
                                    LazyColumn(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                        items(filtered) { chat ->
                                            ChatRow(chat = chat, onClick = { openedChat = chat })
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    val chat = openedChat!!
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                        Text("‹", color = Color.White, fontSize = 32.sp,
                            modifier = Modifier.clickable { openedChat = null }.padding(end = 14.dp))
                        Avatar(chat.avatar, chat.online, 44)
                        Spacer(Modifier.width(12.dp))
                        Column {
                            Text(chat.name, color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
                            Text(if (chat.online) "Online" else "Last seen recently", color = if (chat.online) Color(0xFF55D66B) else Muted, fontSize = 12.sp)
                        }
                    }
                    HorizontalDivider(color = Color(0xFF292933), modifier = Modifier.padding(vertical = 14.dp))
                    Box(Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Your conversation with ${chat.name}", color = Color.White, fontWeight = FontWeight.Medium)
                            Spacer(Modifier.height(8.dp))
                            Text("Live messaging is not connected yet.", color = Muted, fontSize = 12.sp)
                        }
                    }
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = draft, onValueChange = { draft = it }, modifier = Modifier.weight(1f),
                            placeholder = { Text("Write a message…", color = Muted) }, singleLine = true,
                            shape = RoundedCornerShape(24.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White, unfocusedTextColor = Color.White,
                                focusedBorderColor = Accent, unfocusedBorderColor = Color(0xFF30303A)
                            )
                        )
                        Button(onClick = { /* Connect to messaging backend before sending */ }, shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(containerColor = Accent)) { Text("➤") }
                    }
                }
            }

            if (openedChat == null) {
                Row(
                    modifier = Modifier.align(Alignment.BottomCenter).padding(horizontal = 18.dp, vertical = 14.dp)
                        .fillMaxWidth().clip(RoundedCornerShape(28.dp))
                        .background(Color(0xFF191922)).padding(horizontal = 8.dp, vertical = 9.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    listOf("Messages" to "▤", "Calls" to "☎", "Notifications" to "♧", "Profile" to "●").forEach { (tab, glyph) ->
                        Column(horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.clip(RoundedCornerShape(16.dp))
                                .clickable { selectedTab = tab }
                                .padding(horizontal = 10.dp, vertical = 4.dp)) {
                            Text(glyph, color = if (selectedTab == tab) Accent else Muted, fontSize = 20.sp)
                            Text(tab, color = if (selectedTab == tab) Color.White else Muted, fontSize = 10.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ChatRow(chat: ChatPreview, onClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(18.dp))
            .clickable(onClick = onClick).padding(vertical = 12.dp, horizontal = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Avatar(chat.avatar, chat.online, 52)
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text(chat.name, color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
            Spacer(Modifier.height(4.dp))
            Text(chat.message, color = Muted, fontSize = 12.sp, maxLines = 1)
        }
        Column(horizontalAlignment = Alignment.End) {
            Text(chat.time, color = Muted, fontSize = 10.sp)
            if (chat.unread > 0) {
                Spacer(Modifier.height(7.dp))
                Box(Modifier.clip(CircleShape).background(Accent).padding(horizontal = 7.dp, vertical = 3.dp)) {
                    Text(chat.unread.toString(), color = Color.White, fontSize = 10.sp)
                }
            }
        }
    }
}

@Composable
private fun Avatar(label: String, online: Boolean, size: Int) {
    Box {
        Box(
            modifier = Modifier.size(size.dp).clip(CircleShape).background(Color(0xFF29263F)),
            contentAlignment = Alignment.Center
        ) { Text(label, color = Color.White, fontWeight = FontWeight.Bold, fontSize = (size / 2.6).sp) }
        if (online) Box(
            Modifier.align(Alignment.BottomEnd).size(12.dp).clip(CircleShape)
                .background(Color(0xFF55D66B))
        )
    }
}

@Composable
private fun SimpleStateList(title: String, names: List<String>) {
    Column {
        Text(title, color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 18.sp,
            modifier = Modifier.padding(vertical = 12.dp))
        names.forEach { name ->
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp)) {
                Avatar(name.take(1), true, 44)
                Spacer(Modifier.width(12.dp))
                Text(name, color = Color.White, modifier = Modifier.weight(1f))
                Text("›", color = Accent, fontSize = 24.sp)
            }
        }
    }
}
