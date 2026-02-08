package com.example.viikkotehtava2android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.viikkotehtava2android.ui.theme.Viikkotehtava2androidTheme
import com.example.viikkotehtava2android.view.HomeScreen
import com.example.viikkotehtava2android.view.CalendarScreen
import com.example.viikkotehtava2android.view.SettingsScreen
import com.example.viikkotehtava2android.viewmodel.TaskViewModel

const val ROUTE_HOME = "home"
const val ROUTE_CALENDAR = "calendar"
const val ROUTE_SETTINGS = "settings"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Viikkotehtava2androidTheme {
                val navController = rememberNavController()
                val taskViewModel: TaskViewModel = viewModel()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = ROUTE_HOME,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(route = ROUTE_HOME) {
                            HomeScreen(
                                viewModel = taskViewModel,
                                onNavigateToCalendar = { navController.navigate(ROUTE_CALENDAR) },
                                onNavigateToSettings = { navController.navigate(ROUTE_SETTINGS) }
                            )
                        }
                        composable(route = ROUTE_CALENDAR) {
                            CalendarScreen(
                                viewModel = taskViewModel,
                                onNavigateBack = { navController.popBackStack() }
                            )
                        }

                        composable(route = ROUTE_SETTINGS) {
                            SettingsScreen(
                                onNavigateBack = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}