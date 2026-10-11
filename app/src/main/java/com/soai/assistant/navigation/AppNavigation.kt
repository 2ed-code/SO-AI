package com.soai.assistant.navigation

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.soai.assistant.ui.auth.FirebaseAuthScreen
import com.soai.assistant.ui.auth.FirebaseSetupScreen
import com.soai.assistant.ui.home.HomeScreen
import com.soai.assistant.ui.screens.SplashScreen

@Composable
fun AppNavigation() {
    val context = LocalContext.current
    val firebaseApp = remember { FirebaseApp.initializeApp(context) }
    if (firebaseApp == null) {
        FirebaseSetupScreen()
        return
    }

    val auth = remember { FirebaseAuth.getInstance() }
    var signedIn by remember { mutableStateOf(auth.currentUser != null) }
    DisposableEffect(auth) {
        val listener = FirebaseAuth.AuthStateListener { signedIn = it.currentUser != null }
        auth.addAuthStateListener(listener)
        onDispose { auth.removeAuthStateListener(listener) }
    }

    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        composable("splash") {
            SplashScreen {
                navController.navigate(if (signedIn) Routes.HOME else "auth") {
                    popUpTo("splash") { inclusive = true }
                }
            }
        }
        composable("auth") {
            if (signedIn) {
                LaunchedEffect(Unit) {
                    navController.navigate(Routes.HOME) {
                        popUpTo("auth") { inclusive = true }
                    }
                }
            } else {
                FirebaseAuthScreen(onSignedIn = {
                    navController.navigate(Routes.HOME) {
                        popUpTo("auth") { inclusive = true }
                    }
                })
            }
        }
        composable(Routes.HOME) {
            if (signedIn) {
                HomeScreen()
            } else {
                LaunchedEffect(Unit) {
                    navController.navigate("auth") {
                        popUpTo(Routes.HOME) { inclusive = true }
                    }
                }
            }
        }
    }
}
