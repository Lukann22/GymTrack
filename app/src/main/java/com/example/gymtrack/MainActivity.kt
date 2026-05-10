package com.example.gymtrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.gymtrack.ui.history.HistoryScreen
import com.example.gymtrack.ui.home.HomeScreen
import com.example.gymtrack.ui.workout.WorkoutScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GymTrackApp()
        }
    }
}

@Composable
fun GymTrackApp() {
    val navController = rememberNavController()
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
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
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
            composable("home") { HomeScreen() }
            composable("workout") { WorkoutScreen() }
            composable("history") { HistoryScreen() }
        }
    }
}