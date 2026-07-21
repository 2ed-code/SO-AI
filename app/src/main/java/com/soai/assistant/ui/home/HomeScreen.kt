package com.soai.assistant.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.soai.assistant.ui.components.SOGlassCard
import com.soai.assistant.ui.components.SOOrb

@Composable
fun HomeScreen() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF0B0F14),
                        Color(0xFF141A22),
                        Color(0xFF1C2632)
                    )
                )
            )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            SOOrb()

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "SO AI",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Your Premium AI Assistant",
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFFB8C4D4)
            )

            Spacer(modifier = Modifier.height(40.dp))

            SOGlassCard {

                Column(
                    modifier = Modifier.padding(24.dp)
                ) {

                    Text(
                        text = "Status",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Ready to help",
                        color = Color(0xFFB8C4D4)
                    )
                }
            }
        }
    }
}
