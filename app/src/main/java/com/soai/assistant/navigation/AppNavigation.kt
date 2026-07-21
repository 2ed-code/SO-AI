package com.soai.assistant.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.soai.assistant.ui.home.HomeScreen
import com.soai.assistant.ui.screens.SplashScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {

        composable("splash") {
            SplashScreen {
                navController.navigate(Routes.HOME) {
                    popUpTo("splash") {
                        inclusive = true
                    }
                }
            }
        }

        composable(Routes.HOME) {
            HomeScreen()
        }
    }
}
