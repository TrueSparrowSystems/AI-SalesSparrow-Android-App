package com.example.salessparrow.util

sealed class Screens(val route: String) {
    object SplashScreen : Screens("splash_screen")
    object HomeScreen : Screens("home_screen")
    object LoginScreen : Screens("login_screen")
    object NotesScreen :
        Screens("notes_screen/{accountId}/{accountName}/{isAccountSelectionEnabled}");
}
