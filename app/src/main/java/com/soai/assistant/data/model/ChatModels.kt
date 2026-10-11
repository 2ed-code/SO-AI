package com.soai.assistant.data.model

data class UserProfile(
    val uid: String = "",
    val email: String = "",
    val displayName: String = "",
    val primaryColor: Long = 0xFF6A5CFF,
    val secondaryColor: Long = 0xFFA78BFA
)

data class ChatConversation(
    val id: String = "",
    val memberIds: List<String> = emptyList(),
    val memberNames: Map<String, String> = emptyMap(),
    val lastMessage: String = "",
    val updatedAt: Long = 0L
)

data class ChatMessage(
    val id: String = "",
    val conversationId: String = "",
    val senderId: String = "",
    val text: String = "",
    val sentAt: Long = 0L
)
