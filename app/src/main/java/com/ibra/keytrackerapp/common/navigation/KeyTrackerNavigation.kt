package com.ibra.keytrackerapp.common.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ibra.keytrackerapp.key_requests.RequestsScreen
import com.ibra.keytrackerapp.sign_in_sign_up.SignInSignUpScreen

//import com.ibra.keytrackerapp.keytrack.presentation.KeyTrackerScreen

@Composable
fun KeyTrackerNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        //startDestination = Screen.KeyTracker.name
        startDestination = Screen.RequestsScreen.name
    ) {
       // composable(Screen.KeyTracker.name) {
           // KeyTrackerScreen(navController = navController)

       // }
        composable(Screen.RequestsScreen.name) {
            RequestsScreen()
        }
    }

}