package com.soai.assistant.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "🤖 SO AI",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Text(
            text = "مساعدك الشخصي جاهز"
        )

        Spacer(
            modifier = Modifier.height(40.dp)
        )

        Button(
            onClick = {}
        ) {
            Text("🎤 تحدث مع SO")
        }
    }
}
