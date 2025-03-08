package com.example.opendatajabar.ui.health

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.opendatajabar.viewmodel.HealthViewModel

@Composable
fun EntryScreen(navController: NavHostController, viewModel: HealthViewModel) {
    val context = LocalContext.current
    var jumlahPenderita by remember { mutableStateOf("") }
    var satuan by remember { mutableStateOf("") }
    var tahun by remember { mutableStateOf("") }
    var kodeKabupatenKota by remember { mutableStateOf("") }
    var namaKabupatenKota by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Input Data Kesehatan",
                style = MaterialTheme.typography.displayLarge
            )
            OutlinedTextField(
                value = jumlahPenderita,
                onValueChange = { if (it.all { char -> char.isDigit() }) jumlahPenderita = it },
                label = { Text("Jumlah Penderita") },
                textStyle = MaterialTheme.typography.bodyMedium,
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = satuan,
                onValueChange = { satuan = it },
                label = { Text("Satuan") },
                textStyle = MaterialTheme.typography.bodyMedium,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = tahun,
                onValueChange = { if (it.all { char -> char.isDigit() }) tahun = it },
                label = { Text("Tahun") },
                textStyle = MaterialTheme.typography.bodyMedium,
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = kodeKabupatenKota,
                onValueChange = { if (it.all { char -> char.isDigit() }) kodeKabupatenKota = it },
                label = { Text("Kode Kabupaten/Kota") },
                textStyle = MaterialTheme.typography.bodyMedium,
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = namaKabupatenKota,
                onValueChange = { namaKabupatenKota = it },
                label = { Text("Nama Kabupaten/Kota") },
                textStyle = MaterialTheme.typography.bodyMedium,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = {
                    viewModel.insertData(
                        jumlahPenderita = jumlahPenderita.toIntOrNull() ?: 0,
                        satuan = satuan,
                        tahun = tahun.toIntOrNull() ?: 0,
                        kodeKabupatenKota = kodeKabupatenKota.toIntOrNull() ?: 0,
                        namaKabupatenKota = namaKabupatenKota,
                    )
                    Toast.makeText(context, "Data berhasil ditambahkan!", Toast.LENGTH_SHORT).show()
                    // Navigasi ke tampilan daftar data
                    navController.navigate("health")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Submit Data",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) {
                Text(
                    text = "Back",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
