package com.example.salessparrow.services

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.salessparrow.screens.HomeScreen
import com.example.salessparrow.screens.SplashScreen
import com.example.salessparrow.screens.LogInScreen
import com.example.salessparrow.util.Screens
import com.example.salessparrow.viewmodals.AuthenticationViewModal

object NavigationService {
    private lateinit var navController: NavController
    private lateinit var authenticationViewModal: AuthenticationViewModal;

    /**
     * Initializes the NavigationService with the NavHostController.
     */
    fun initialize(navController: NavController, authenticationViewModal: AuthenticationViewModal) {
        this.navController = navController
        this.authenticationViewModal = authenticationViewModal;

    }

    /**
     * Navigates to the specified screen using its screen name.
     **/
    fun navigateTo(screenName: String) {
        navController.navigate(screenName)
    }

    /**
     * Navigates to the specified screen using its screen name and pops up to the specified screen.
     **/
    fun navigateWithPopUp(screenName: String, popUpTo: String) {
        navController.navigate(screenName) {
            popUpTo(popUpTo) {
                inclusive = true
            }
        }
    }

    /**
     * Navigates to the specified screen using its screen name along with arguments.
     */
    fun navigateToWithArgs(screenName: String, argsBuilder: NavOptionsBuilder.() -> Unit) {
        navController.navigate(screenName, argsBuilder)
    }

    /**
     * Navigates to the back screen.
     */
    fun navigateBack() {
        navController.popBackStack()
    }

    /**
     * Navigates to the appropriate screen based on the user's login status.
     */
    fun navigateBasedOnLoginStatus() {
        Log.i(
            "NavigationService",
            "navigateBasedOnLoginStatus : ${authenticationViewModal.isUserLoggedIn()}"
        )
        if (authenticationViewModal.isUserLoggedIn()) {
            navigateWithPopUp(Screens.HomeScreen.route, Screens.SplashScreen.route)
        } else {
            navigateWithPopUp(Screens.LoginScreen.route, Screens.SplashScreen.route);
        }
    }


}

@Composable
fun NavigationService() {
    val navController = rememberNavController()
    val authenticationViewModal: AuthenticationViewModal = viewModel()
    NavigationService.initialize(navController, authenticationViewModal)

    NavHost(navController = navController, startDestination = Screens.SplashScreen.route) {
        composable(route = Screens.LoginScreen.route) { LogInScreen() }
        composable(route = Screens.HomeScreen.route) { HomeScreen() }
        composable(route = Screens.SplashScreen.route) {
            NavigationService.navigateBasedOnLoginStatus();
        }
    }
}

