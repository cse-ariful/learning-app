package com.online.learning.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.online.learning.features.calendar.CalendarPage
import com.online.learning.features.home.HomeScreen
import com.online.learning.features.splash.OnBoardingScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavigator() {
    val navController = rememberAnimatedNavController()
    BaseAppNavHost(navController = navController, startDestination = Screens.CALENDAR.route) {
        composable(Screens.SPLASH.route) {
            OnBoardingScreen(navController) {
                navController.navigate(Screens.HOME.route)
            }
        }
        composable(Screens.HOME.route) {
            HomeScreen(navController)
        }
        composable(Screens.CALENDAR.route) {
            CalendarPage(navController)
        }
    }
}