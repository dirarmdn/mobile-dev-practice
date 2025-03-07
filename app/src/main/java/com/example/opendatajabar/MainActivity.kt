package com.example.opendatajabar

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.opendatajabar.ui.AppNavHost
import com.example.opendatajabar.ui.theme.HanyarunrunTheme
import com.example.opendatajabar.viewmodel.RegionViewModel
import com.example.opendatajabar.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "onCreate dipanggil")

        setContent {
            HanyarunrunTheme {
                Log.d("MainActivity", "SetContent dipanggil")

                val regionViewModel: RegionViewModel = viewModel()
                val userViewModel: UserViewModel = viewModel()

                AppNavHost(viewModel = regionViewModel, userViewModel = userViewModel)
            }
        }
    }
}
