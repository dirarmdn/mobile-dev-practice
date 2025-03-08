package com.example.opendatajabar.ui.custom

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen (
    val id : String,
    val title : String,
    val icon : ImageVector
) {
    object Home : Screen("home", "Home", Icons.Default.Home)
    object Data : Screen("data", "Data", Icons.Default.Info)
    object Profile : Screen("profile", "Profile", Icons.Default.AccountCircle)

    object Items{
        val list = listOf(
            Home,Data,Profile
        )
    }
}