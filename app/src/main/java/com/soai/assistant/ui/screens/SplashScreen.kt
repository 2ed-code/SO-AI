package com.soai.assistant.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.soai.assistant.ui.components.SOOrb

@Composable
fun SplashScreen(
    onFinish: () -> Unit
) {

    val alpha by rememberInfiniteTransition(label = "")
        .animateFloat(
            initialValue = 0.4f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                tween(1500),
                RepeatMode.Reverse
            ),
            label = ""
        )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            SOOrb()

            Spacer(
                modifier = Modifier.height(40.dp)
            )

            Text(
                text = "SO AI",
                style = MaterialTheme.typography.headlineLarge
            )

            Text(
                text = "Smart Assistant",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
