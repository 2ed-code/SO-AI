package com.soai.assistant.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.soai.assistant.data.model.ChatConversation
import com.soai.assistant.data.model.ChatMessage
import kotlinx.coroutines.tasks.await

class ChatRepository(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    suspend fun createDirectConversation(
        currentUid: String,
        currentName: String,
        otherUid: String,
        otherName: String
    ): String {
        require(currentUid.isNotBlank() && otherUid.isNotBlank()) { "Both user IDs are required." }
        require(currentUid != otherUid) { "You cannot start a conversation with yourself." }

        val members = listOf(currentUid, otherUid).sorted()
        val existing = db.collection("conversations")
            .whereArrayContains("memberIds", currentUid)
            .get().await().documents.firstOrNull { doc ->
                (doc.get("memberIds") as? List<*>)?.filterIsInstance<String>()?.sorted() == members
            }
        if (existing != null) return existing.id

        val ref = db.collection("conversations").document()
        val conversation = ChatConversation(
            id = ref.id,
            memberIds = members,
            memberNames = mapOf(currentUid to currentName, otherUid to otherName),
            updatedAt = System.currentTimeMillis()
        )
        ref.set(conversation).await()
        return ref.id
    }

    fun observeConversations(
        uid: String,
        onUpdate: (List<ChatConversation>) -> Unit,
        onError: (Exception) -> Unit
    ): ListenerRegistration {
        return db.collection("conversations")
            .whereArrayContains("memberIds", uid)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    onError(error)
                    return@addSnapshotListener
                }
                onUpdate(snapshot?.documents.orEmpty().mapNotNull { doc ->
                    doc.toObject(ChatConversation::class.java)?.copy(id = doc.id)
                }.sortedByDescending { it.updatedAt })
            }
    }

    fun observeMessages(
        conversationId: String,
        onUpdate: (List<ChatMessage>) -> Unit,
        onError: (Exception) -> Unit
    ): ListenerRegistration {
        return db.collection("conversations").document(conversationId)
            .collection("messages").orderBy("sentAt")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    onError(error)
                    return@addSnapshotListener
                }
                onUpdate(snapshot?.documents.orEmpty().mapNotNull { doc ->
                    doc.toObject(ChatMessage::class.java)?.copy(id = doc.id)
                })
            }
    }

    suspend fun sendMessage(conversationId: String, senderUid: String, text: String) {
        val cleanText = text.trim()
        require(cleanText.isNotEmpty()) { "Message cannot be empty." }
        require(conversationId.isNotBlank() && senderUid.isNotBlank()) { "Conversation and sender are required." }

        val now = System.currentTimeMillis()
        val messageRef = db.collection("conversations").document(conversationId)
            .collection("messages").document()
        val message = ChatMessage(
            id = messageRef.id,
            conversationId = conversationId,
            senderId = senderUid,
            text = cleanText,
            sentAt = now
        )
        db.runBatch { batch ->
            batch.set(messageRef, message)
            batch.update(
                db.collection("conversations").document(conversationId),
                mapOf("lastMessage" to cleanText, "updatedAt" to now)
            )
        }.await()
    }
}
