package com.example.gymtrack

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.gymtrack.ui.history.HistoryScreen
import com.example.gymtrack.ui.home.HomeScreen
import com.example.gymtrack.ui.template.TemplateDetailScreen
import com.example.gymtrack.ui.viewmodel.WorkoutViewModel
import com.example.gymtrack.ui.workout.WorkoutScreen
import java.util.concurrent.TimeUnit
/**
 * Main entry point of the GymTrack application.
 * Initializes notification channel and WorkManager daily reminder.
 * Sets up Jetpack Compose UI with navigation.
 */
class MainActivity : ComponentActivity() {
    /**
     * Called when the activity is first created.
     * Sets up notification channel, WorkManager reminder and Compose UI.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        val reminderRequest = PeriodicWorkRequestBuilder<ReminderWorker>(1, TimeUnit.MINUTES)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "daily_reminder",
            ExistingPeriodicWorkPolicy.KEEP,
            reminderRequest
        )
        super.onCreate(savedInstanceState)
        NotificationHelper.createNotificationChannel(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 0)
        }
        setContent {
            GymTrackApp()
        }
    }
}
/**
 * Root composable function that sets up the entire app navigation.
 * Contains bottom navigation bar with Home, Workout and History tabs.
 * Manages navigation between all screens using NavController.
 */
@Composable
fun GymTrackApp() {
    val navController = rememberNavController()
    val workoutViewModel: WorkoutViewModel = viewModel()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val backgroundColor = Color(0xFF0A0A0A)
    val greenColor = Color(0xFF4ADE80)
    val grayColor = Color(0xFF9CA3AF)

    Scaffold(
        containerColor = backgroundColor,
        bottomBar = {
            NavigationBar(containerColor = Color(0xFF1A1A1A)) {
                listOf(
                    Triple("home", "Home", "🏠"),
                    Triple("workout", "Workout", "💪"),
                    Triple("history", "History", "📋")
                ).forEach { (route, label, icon) ->
                    NavigationBarItem(
                        selected = currentDestination?.hierarchy?.any { it.route == route } == true,
                        onClick = {
                            navController.navigate(route) {
                                popUpTo("home")
                                launchSingleTop = true
                            }
                        },
                        icon = { Text(icon) },
                        label = { Text(label) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = greenColor,
                            selectedTextColor = greenColor,
                            unselectedIconColor = grayColor,
                            unselectedTextColor = grayColor,
                            indicatorColor = Color(0xFF1A1A1A)
                        )
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("workout") { WorkoutScreen(workoutViewModel = workoutViewModel) }
            composable("home") {
                HomeScreen(
                    onStartWorkout = { navController.navigate("workout") { launchSingleTop = true } },
                    onTemplateClick = { id, name -> navController.navigate("template/$id/$name") }
                )
            }

            composable("history") { HistoryScreen() }
            composable("template/{templateId}/{templateName}") { backStackEntry ->
                val templateId = backStackEntry.arguments?.getString("templateId")?.toLong() ?: 0L
                val templateName = backStackEntry.arguments?.getString("templateName") ?: ""
                TemplateDetailScreen(
                    templateId = templateId,
                    templateName = templateName,
                    onBack = { navController.popBackStack() },
                    onStartWorkout = { name, exercises ->
                        workoutViewModel.startWorkoutFromTemplate(name, exercises)
                        navController.navigate("workout") {
                            popUpTo("home")
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }

}