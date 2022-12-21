package com.online.learning.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.online.learning.features.splash.OnBoardingScreen

@Composable
fun AppNavigator() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.SPLASH.route){
        composable(Screens.SPLASH.route){
            OnBoardingScreen(navController)
        }
    }
}