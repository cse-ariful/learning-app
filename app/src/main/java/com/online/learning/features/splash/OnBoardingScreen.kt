package com.online.learning.features.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
enum class OnBoardingSteps{
    STEP_1,
    STEP_2,
    STEP_3,
    STEP_4
}
@Composable
fun OnBoardingScreen(navController: NavController) {
    val controller = rememberNavController()
    NavHost(navController =controller , startDestination =OnBoardingSteps.STEP_1.name){
        composable(OnBoardingSteps.STEP_1.name){
            OnBoardingStep1(navController = controller)
        }
        composable(OnBoardingSteps.STEP_2.name){
            OnBoardingStep2(navController = controller)
        }
    }
}
@Composable
fun OnBoardingStep1(navController: NavController){
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.Red)) {

    }
}

@Composable
fun OnBoardingStep2(navController: NavController){
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.Blue)) {

    }
}