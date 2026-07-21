package com.soai.assistant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.soai.assistant.core.theme.SOAITheme
import com.soai.assistant.ui.home.HomeScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SOAITheme {
                HomeScreen()
            }
        }
    }
}
