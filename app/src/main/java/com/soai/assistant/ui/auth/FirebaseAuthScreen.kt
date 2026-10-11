package com.soai.assistant.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soai.assistant.data.repository.AuthRepository
import kotlinx.coroutines.launch

@Composable
fun FirebaseAuthScreen(onSignedIn: () -> Unit) {
    val repo = remember { AuthRepository() }
    val scope = rememberCoroutineScope()
    var register by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var busy by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    Surface(Modifier.fillMaxSize(), color = Color(0xFF09090D)) {
        Column(
            Modifier.fillMaxWidth().padding(24.dp).wrapContentHeight(Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("207200", color = Color(0xFF6A5CFF), fontSize = 42.sp, fontWeight = FontWeight.Black)
            Spacer(Modifier.height(8.dp))
            Text(if (register) "Create your account" else "Welcome back", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(24.dp))
            OutlinedTextField(
                value = email, onValueChange = { email = it }, label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(), singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White, focusedBorderColor = Color(0xFF6A5CFF), unfocusedBorderColor = Color.DarkGray)
            )
            Spacer(Modifier.height(12.dp))
            OutlinedTextField(
                value = password, onValueChange = { password = it }, label = { Text("Password (6+ characters)") },
                modifier = Modifier.fillMaxWidth(), singleLine = true, visualTransformation = PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White, focusedBorderColor = Color(0xFF6A5CFF), unfocusedBorderColor = Color.DarkGray)
            )
            error?.let { Text(it, color = Color(0xFFFF7272), modifier = Modifier.padding(top = 12.dp)) }
            Spacer(Modifier.height(18.dp))
            Button(
                onClick = {
                    scope.launch {
                        busy = true
                        error = null
                        try {
                            if (register) repo.register(email, password) else repo.signIn(email, password)
                            onSignedIn()
                        } catch (e: Exception) {
                            error = e.localizedMessage ?: "Authentication failed."
                        } finally { busy = false }
                    }
                },
                enabled = !busy,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A5CFF))
            ) {
                Text(if (busy) "Please wait…" else if (register) "Create account" else "Sign in")
            }
            TextButton(onClick = { register = !register; error = null }) {
                Text(if (register) "Already have an account? Sign in" else "New here? Create an account", color = Color(0xFFA78BFA))
            }
        }
    }
}

@Composable
fun FirebaseSetupScreen() {
    Surface(Modifier.fillMaxSize(), color = Color(0xFF09090D)) {
        Column(Modifier.padding(24.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Text("207200", color = Color(0xFF6A5CFF), fontSize = 42.sp, fontWeight = FontWeight.Black)
            Spacer(Modifier.height(16.dp))
            Text("Firebase setup required", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(12.dp))
            Text(
                "Create a Firebase project, register an Android app with package name com.soai.assistant, enable Email/Password sign-in and Cloud Firestore, then add the project's google-services.json and Google Services Gradle plugin. Real accounts and messages remain disabled until this configuration is supplied.",
                color = Color(0xFFB6B6C4), style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
