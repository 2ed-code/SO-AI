package com.soai.assistant.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onFinish: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(700)
        onFinish()
    }
    Box(
        modifier = Modifier.fillMaxSize().background(Color(0xFF09090D)),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("207200", color = Color(0xFF6A5CFF), fontSize = 44.sp, fontWeight = FontWeight.Black)
            Spacer(Modifier.height(8.dp))
            Text("Your people, your conversations", color = Color(0xFF9A9AA8), style = MaterialTheme.typography.bodyMedium)
        }
    }
}
