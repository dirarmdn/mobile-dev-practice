package com.example.opendatajabar.ui

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.opendatajabar.data.RegionEntity
import com.example.opendatajabar.viewmodel.RegionViewModel

@Composable
fun EditScreen(
    navController: NavHostController,
    viewModel: RegionViewModel,
    dataId: Int
) {
    val context = LocalContext.current

    var kodeProvinsi by remember { mutableStateOf("") }
    var namaProvinsi by remember { mutableStateOf("") }
    var kodeKabupatenKota by remember { mutableStateOf("") }
    var namaKabupatenKota by remember { mutableStateOf("") }

    val regionData by viewModel.regionData.collectAsState()

    LaunchedEffect(dataId) {
        viewModel.fetchRegionById(dataId)
    }

    LaunchedEffect(regionData) {
        regionData?.let { data ->
            kodeProvinsi = data.kodeProvinsi.toString()
            namaProvinsi = data.namaProvinsi
            kodeKabupatenKota = data.kodeKabupatenKota.toString()
            namaKabupatenKota = data.namaKabupatenKota
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Edit Data",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            OutlinedTextField(
                value = kodeProvinsi,
                onValueChange = { if (it.all { char -> char.isDigit() }) kodeProvinsi = it },
                label = { Text("Kode Provinsi") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = namaProvinsi,
                onValueChange = { namaProvinsi = it },
                label = { Text("Nama Provinsi") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = kodeKabupatenKota,
                onValueChange = { if (it.all { char -> char.isDigit() }) kodeKabupatenKota = it },
                label = { Text("Kode Kabupaten/Kota") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = namaKabupatenKota,
                onValueChange = { namaKabupatenKota = it },
                label = { Text("Nama Kabupaten/Kota") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    val updatedData = RegionEntity(
                        id = dataId,
                        kodeProvinsi = kodeProvinsi.toIntOrNull() ?: 0,
                        namaProvinsi = namaProvinsi,
                        kodeKabupatenKota = kodeKabupatenKota.toIntOrNull() ?: 0,
                        namaKabupatenKota = namaKabupatenKota,
                    )
                    viewModel.updateData(updatedData)
                    Toast.makeText(context, "Data berhasil diupdate!", Toast.LENGTH_SHORT).show()
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Update Data", style = MaterialTheme.typography.bodyMedium)
            }
            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier.fillMaxSize(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) {
                Text(text = "Back", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
