package com.example.opendatajabar.ui.health

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
import com.example.opendatajabar.data.HealthEntity
import com.example.opendatajabar.viewmodel.HealthViewModel

@Composable
fun EditHealthScreen(
    navController: NavHostController,
    viewModel: HealthViewModel,
    dataId: Int
) {
    val context = LocalContext.current

    var jumlahPenderita by remember { mutableStateOf("") }
    var satuan by remember { mutableStateOf("") }
    var tahun by remember { mutableStateOf("") }
    var kodeKabupatenKota by remember { mutableStateOf("") }
    var namaKabupatenKota by remember { mutableStateOf("") }

    val healthData by viewModel.healthData.collectAsState()

    LaunchedEffect(dataId) {
        viewModel.fetchHealthById(dataId)
    }

    LaunchedEffect(healthData) {
        println("Health data updated: $healthData")
        healthData?.let { data ->
            jumlahPenderita = data.jumlahPenderita.toString()
            satuan = data.satuan
            tahun = data.tahun.toString()
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
                text = "Edit Data Kesehatan",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            OutlinedTextField(
                value = jumlahPenderita,
                onValueChange = { if (it.all { char -> char.isDigit() }) jumlahPenderita = it },
                label = { Text("Jumlah Penderita") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = satuan,
                onValueChange = { satuan = it },
                label = { Text("Satuan") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = tahun,
                onValueChange = { if (it.all { char -> char.isDigit() }) tahun = it },
                label = { Text("Tahun") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
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
                    val updatedData = HealthEntity(
                        id = dataId,
                        jumlahPenderita = jumlahPenderita.toIntOrNull() ?: 0,
                        satuan = satuan,
                        tahun = tahun.toIntOrNull() ?: 0,
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
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) {
                Text(text = "Back", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
