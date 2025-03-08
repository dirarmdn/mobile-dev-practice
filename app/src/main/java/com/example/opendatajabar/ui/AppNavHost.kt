package com.example.opendatajabar.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.opendatajabar.ui.health.EditHealthScreen
import com.example.opendatajabar.ui.health.EntryScreen
import com.example.opendatajabar.ui.health.HealthListScreen
import com.example.opendatajabar.viewmodel.HealthViewModel
import com.example.opendatajabar.viewmodel.RegionViewModel
import com.example.opendatajabar.viewmodel.UserViewModel

@Composable
fun AppNavHost(viewModel: RegionViewModel, userViewModel: UserViewModel, healthViewModel: HealthViewModel) {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route ?: "list"

    Scaffold(
        bottomBar = {
            CustomBottomNavigation(
                currentScreenId = currentRoute,
                onItemSelected = { screen ->
                    navController.navigate(screen.id) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") {
                HomeScreen(navController = navController)
            }

            composable("data") {
                DataScreen(navController = navController, viewModel = healthViewModel)
            }
            composable("form") {
                DataEntryScreen(navController = navController, viewModel = viewModel)
            }
            composable("list") {
                DataListScreen(navController = navController, viewModel = viewModel)
            }
            composable("health") {
                HealthListScreen(navController = navController, viewModel = healthViewModel)
            }
            composable("health-form") {
                EntryScreen(navController = navController, viewModel = healthViewModel)
            }
            composable("profile") {
                ProfileScreen(viewModel = userViewModel)
            }
            composable(
                route = "edit/{id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("id") ?: 0
                EditScreen(navController = navController, viewModel = viewModel, dataId = id)
            }
            composable(
                route = "edit/health/{id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("id") ?: 0
                EditHealthScreen(navController = navController, viewModel = healthViewModel, dataId = id)
            }
        }
    }
}
