package com.ibra.keytrackerapp.common.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.ibra.keytrackerapp.key_requests.presentation.RequestsScreen
import com.ibra.keytrackerapp.login.LoginScreen
import com.ibra.keytrackerapp.sign_in_sign_up.SignInSignUpScreen

@Composable
fun KeyTrackerNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.SignInSignUpScreen.name
    ) {
        composable(Screen.SignInSignUpScreen.name) {
            SignInSignUpScreen(navController = navController)
        }
        composable(Screen.Login.name) {
            LoginScreen(navController = navController)
        }
        composable(Screen.RequestsScreen.name){
            RequestsScreen(navController = navController)
        }
    }
}

