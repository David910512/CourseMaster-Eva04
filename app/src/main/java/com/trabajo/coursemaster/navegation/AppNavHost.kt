package com.trabajo.coursemaster.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.trabajo.coursemaster.ui.auth.LoginScreen
import com.trabajo.coursemaster.ui.auth.RegisterScreen
import com.trabajo.coursemaster.ui.product.ProductScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        // Rutas de autenticación
        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("products") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate("register")
                }
            )
        }

        composable("register") {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate("products") {
                        popUpTo("register") { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.popBackStack()
                }
            )
        }

        // Ruta de productos
        composable("products") {
            ProductScreen()
        }
    }
}