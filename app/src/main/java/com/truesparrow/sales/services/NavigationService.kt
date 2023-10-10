package com.truesparrow.sales.services

import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import com.google.gson.Gson
import com.truesparrow.sales.BuildConfig
import com.truesparrow.sales.models.EventDetailsObject
import com.truesparrow.sales.models.NoteData
import com.truesparrow.sales.models.NoteDetailsObject
import com.truesparrow.sales.models.TaskData
import com.truesparrow.sales.screens.AccountDetails
import com.truesparrow.sales.screens.EventScreen
import com.truesparrow.sales.screens.HomeScreen
import com.truesparrow.sales.screens.SplashScreen
import com.truesparrow.sales.screens.LogInScreen
import com.truesparrow.sales.screens.NoteDetailScreen
import com.truesparrow.sales.screens.NotesScreen
import com.truesparrow.sales.screens.SettingsScreen
import com.truesparrow.sales.screens.TaskScreen
import com.truesparrow.sales.util.Screens
import com.truesparrow.sales.util.extractDateAndTime
import com.truesparrow.sales.viewmodals.AuthenticationViewModal


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
        navController.navigate(screenName) {
            launchSingleTop = true
        }
    }

    /**
     * Navigates to the specified screen using its screen name and pops up to the specified screen.
     **/
    fun navigateWithPopUp(screenName: String, popUpTo: String) {
        navController.navigate(screenName) {
            launchSingleTop = true
            popUpTo(popUpTo) {
                inclusive = true
            }
        }
    }

    fun navigateToNotesScreen(
        accountId: String,
        accountName: String,
        isAccountSelectionEnabled: Boolean,
        noteData: NoteData?
    ) {
        val noteDataJson = noteData?.let { Gson().toJson(it) } ?: "null_placeholder"

        navController.navigate("notes_screen/$accountId/$accountName/$isAccountSelectionEnabled/$noteDataJson") {
            launchSingleTop = true
        }
    }

    fun navigateToEventScreen(
        accountId: String,
        eventData: EventDetailsObject?
    ) {
        val eventDataJson = eventData?.let { Gson().toJson(it) } ?: "null_placeholder"

        navController.navigate("event_screen/$accountId/$eventDataJson") {
            launchSingleTop = true
        }
    }

    fun navigateToTaskScreen(
        accountId: String,
        accountName: String,
        taskData: TaskData?
    ) {
        val taskDataJson = taskData?.let { Gson().toJson(it) } ?: "null_placeholder"

        navController.navigate("task_screen/$accountId/$accountName/$taskDataJson") {
            launchSingleTop = true
        }
    }


    fun navigateWithPopUpClearingAllStack(screenName: String) {
        navController.navigate(screenName) {
            popUpTo(0)
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
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationService(intent: Intent?) {
    val navController = rememberNavController()
    val authenticationViewModal: AuthenticationViewModal = viewModel()
    NavigationService.initialize(navController, authenticationViewModal)

    NavHost(navController = navController, startDestination = Screens.SplashScreen.route) {
        composable(route = Screens.SplashScreen.route) {
            SplashScreen()
        }
        composable(
            route = Screens.LoginScreen.route,
            deepLinks = listOf(navDeepLink {
                uriPattern = BuildConfig.REDIRECT_URI
                action = Intent.ACTION_VIEW
            })
        ) {
            LogInScreen(intent = intent)
        }
        composable(route = Screens.HomeScreen.route) { HomeScreen() }
        composable(
            route = Screens.NotesScreen.route
        ) {
            val accountId = it.arguments?.getString("accountId") ?: ""
            val accountName = it.arguments?.getString("accountName") ?: ""
            val isAccountSelectionEnabled =
                it.arguments?.getString("isAccountSelectionEnabled")?.toBoolean() ?: false
            val noteDataJson = it.arguments?.getString("noteData") ?: ""
            val noteData = if (noteDataJson != "null_placeholder") Gson().fromJson(
                noteDataJson,
                NoteDetailsObject::class.java
            ) else null

            if (noteData != null) {
                NotesScreen(
                    accountName,
                    accountId,
                    isAccountSelectionEnabled,
                    noteData.id,
                    noteData.text,
                    noteData.shouldShowCrmSuggestion
                )
            } else {
                NotesScreen(accountName, accountId, isAccountSelectionEnabled, "", "", true)
            }
        }
        composable(route = Screens.AccountDetailsScreen.route) {
            val accountId = it.arguments?.getString("accountId") ?: ""
            val accountName = it.arguments?.getString("accountName") ?: ""
            AccountDetails(accountId, accountName)
        }
        composable(route = Screens.SettingsScreen.route) {
            SettingsScreen()
        }
        composable(route = Screens.NoteDetailsScreen.route) {
            val accountId = it.arguments?.getString("accountId") ?: ""
            val accountName = it.arguments?.getString("accountName") ?: ""
            val noteId = it.arguments?.getString("noteId") ?: ""
            NoteDetailScreen(accountId, accountName, noteId)
        }

        composable(route = Screens.TaskScreen.route) {
            val accountId = it.arguments?.getString("accountId") ?: ""
            val accountName = it.arguments?.getString("accountName") ?: ""
            val taskDataJson = it.arguments?.getString("taskData") ?: ""
            val taskData = if (taskDataJson != "null_placeholder") Gson().fromJson(
                taskDataJson,
                TaskData::class.java
            ) else null

            if (taskData != null) {
                TaskScreen(
                    accountId,
                    accountName,
                    taskData.description ?: "",
                    taskData.due_date ?: "",
                    taskData.crm_organization_user_id ?: "",
                    taskData.crm_organization_user_name ?: "",
                    taskData.id ?: "",

                    )
            } else {
                TaskScreen(
                    accountId,
                    accountName,
                    "",
                    "",
                    "",
                    "",
                    "",
                )
            }
        }

        composable(route = Screens.EventScreen.route) {
            val accountId = it.arguments?.getString("accountId") ?: ""
            val accountName = it.arguments?.getString("accountName") ?: ""
            val eventDataJson = it.arguments?.getString("eventData") ?: ""
            val eventData = if (eventDataJson != "null_placeholder") Gson().fromJson(
                eventDataJson,
                EventDetailsObject::class.java
            ) else null

            var startDateTime =
                if (eventDataJson != "null_placeholder") extractDateAndTime(eventData!!.eventStartDate) else Pair(
                    "",
                    ""
                )
            var endDateTime =
                if (eventDataJson != "null_placeholder") extractDateAndTime(eventData!!.eventEndDate) else Pair(
                    "",
                    ""
                )


            Log.i(
                "EventScreen",
                "accountId: $accountId, accountName: $accountName eventData: $eventData  $startDateTime $endDateTime "
            )

            if (eventData != null) {
                EventScreen(
                    accountId,
                    startDateTime?.first ?: "",
                    endDateTime?.first ?: "",
                    startDateTime?.second ?: "",
                    endDateTime?.second ?: "",
                    eventData.eventDescription ?: "",
                    eventData.eventId ?: ""
                )

            } else {
                EventScreen(
                    accountId,
                    "",
                    "",
                    "",
                    "",
                    "",
                    ""
                )
            }

        }

    }
}

