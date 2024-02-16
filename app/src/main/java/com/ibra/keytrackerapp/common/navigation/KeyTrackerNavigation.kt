package com.ibra.keytrackerapp.common.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ibra.keytrackerapp.keytrack.presentation.KeyTrackerScreen

@Composable
fun KeyTrackerNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.KeyTracker.name
    ) {
        composable(Screen.KeyTracker.name) {
            KeyTrackerScreen(navController = navController)
        }
    }

}