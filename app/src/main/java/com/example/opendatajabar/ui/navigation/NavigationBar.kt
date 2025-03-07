package com.example.opendatajabar.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun NavigationBar(navController: NavController, currentRoute: String?) {
    NavigationBar {
        val items = listOf(
            BottomNavItem("list", "Home", Icons.Filled.Home),
            BottomNavItem("form", "Form", Icons.Filled.Create),
            BottomNavItem("profile", "Profile", Icons.AutoMirrored.Filled.List),
        )

        items.forEach { item ->
            NavigationBarItem(
                label = { Text(item.label) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo("list") { inclusive = false }
                    }
                },
                icon = { Icon(imageVector = item.icon, contentDescription = item.label) }
            )
        }
    }
}

data class BottomNavItem(val route: String, val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector)
