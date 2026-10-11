package com.soai.assistant.core.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val SOChatColors = darkColorScheme(
    primary = Color(0xFF6A5CFF),
    onPrimary = Color.White,
    secondary = Color(0xFFA78BFA),
    background = Color(0xFF09090D),
    onBackground = Color.White,
    surface = Color(0xFF17171F),
    onSurface = Color.White
)

@Composable
fun SOAITheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = SOChatColors,
        content = content
    )
}
