package com.ym.self_learn

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.ym.learn.login.LoginPhoneScreen

@Composable
fun Navigation(navController: NavHostController = rememberNavController(), isLoggedIn: Boolean) {
    NavHost(
        startDestination = if (isLoggedIn) "main" else "login",
        navController = navController,
        modifier = Modifier,
    ) {
        navigation(
            startDestination = "loginPhone",
            route = "login"
        ) {
            composable("loginPhone") {
                LoginPhoneScreen(navController)
            }
            composable("loginPin") {
                LoginPhoneScreen(navController)
            }
        }

        navigation(
            startDestination = "tabs",
            route = "main"
        ) {
            composable("tabs") {
                MainScreen()
            }
        }

    }
}