package com.soai.assistant.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.soai.assistant.ui.components.SOGlassCard
import com.soai.assistant.ui.components.SOOrb

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
            text = "SO AI",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(Modifier.height(18.dp))

        SOOrb()

        Spacer(Modifier.height(36.dp))

        SOGlassCard {
            Text(
                text = "جاهز للمساعدة"
            )
        }
    }
}
