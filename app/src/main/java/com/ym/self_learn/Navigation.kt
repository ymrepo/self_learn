package com.ym.self_learn

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ym.discover.DiscoverMain
import com.ym.learn.login.LoginPage
import com.ym.self_learn.example.GameScreen

@Composable
fun Navigation(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = "example/game") {
        composable("login") {
            LoginPage()
        }
        composable("discover") {
            DiscoverMain()
        }
        composable("example/game"){
            GameScreen()
        }
    }
}