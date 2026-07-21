package com.soai.assistant.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SOOrb() {

    val transition = rememberInfiniteTransition(label = "")

    val scale by transition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            tween(2500),
            RepeatMode.Reverse
        ),
        label = ""
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size((120 * scale).dp)
            .shadow(40.dp, CircleShape)
            .background(
                Brush.radialGradient(
                    listOf(
                        Color(0xFFEAF2FF),
                        Color(0xFF8FA8C9),
                        Color(0xFF3E4E63)
                    )
                ),
                CircleShape
            )
    )
}
