package com.truesparrow.sales.util

sealed class Screens(val route: String) {
    object SplashScreen : Screens("splash_screen")
    object HomeScreen : Screens("home_screen")
    object LoginScreen : Screens("login_screen")
    object NotesScreen :
        Screens("notes_screen/{accountId}/{accountName}/{isAccountSelectionEnabled}");
    object AccountDetailsScreen : Screens("account_details_screen/{accountId}/{accountName}")

    object NoteDetailsScreen : Screens("note_details_screen/{accountId}/{accountName}/{noteId}")
    object SettingsScreen : Screens("settings_screen")
    object AddRecommendedTaskScreen : Screens("recommended_add_task_screen/{crmUserId}/{crmUserName}")
}
