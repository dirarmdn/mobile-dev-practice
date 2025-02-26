package com.example.hanyarunrun.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.hanyarunrun.viewmodel.DataViewModel

@Composable
fun AppNavHost(viewModel: DataViewModel) {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    Scaffold(
        bottomBar = {
            NavigationBar(navController = navController, currentRoute = currentRoute)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "list",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("form") {
                DataEntryScreen(navController = navController, viewModel = viewModel)
            }
            composable("list") {
                DataListScreen(navController = navController, viewModel = viewModel)
            }
            composable("profile") {
                ProfileScreen()
            }
            composable(
                route = "edit/{id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("id") ?: 0
                EditScreen(navController = navController, viewModel = viewModel, dataId = id)
            }
        }
    }
}
