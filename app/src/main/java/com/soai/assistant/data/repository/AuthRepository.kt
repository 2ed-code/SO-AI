package com.soai.assistant.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class AuthRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
) {
    val currentUser: FirebaseUser?
        get() = auth.currentUser

    suspend fun register(email: String, password: String): FirebaseUser {
        require(email.isNotBlank()) { "Enter your email address." }
        require(password.length >= 6) { "Password must contain at least 6 characters." }
        return auth.createUserWithEmailAndPassword(email.trim(), password).await().user
            ?: error("Account was created but no user session was returned.")
    }

    suspend fun signIn(email: String, password: String): FirebaseUser {
        require(email.isNotBlank()) { "Enter your email address." }
        require(password.isNotBlank()) { "Enter your password." }
        return auth.signInWithEmailAndPassword(email.trim(), password).await().user
            ?: error("Sign-in completed without a user session.")
    }

    fun signOut() = auth.signOut()
}
