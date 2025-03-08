package com.example.opendatajabar.ui.health

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.opendatajabar.viewmodel.HealthViewModel

@Composable
fun HealthListScreen(
    navController: NavHostController,
    viewModel: HealthViewModel = hiltViewModel()
) {
    val healthList = viewModel.healthList.collectAsLazyPagingItems()
    val context = LocalContext.current
    Log.d("HealthViewModel", "Jumlah data: ${healthList.itemCount}")

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("health-form") },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Tambah Data")
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when {
                healthList.loadState.refresh is LoadState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                healthList.loadState.refresh is LoadState.Error -> {
                    val error = (healthList.loadState.refresh as LoadState.Error).error
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Error: ${error.localizedMessage}",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }

                healthList.itemCount == 0 -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No Data Available",
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(healthList.itemCount) { index ->
                            val item = healthList[index]
                            if (item != null) {
                                Card(
                                    shape = RoundedCornerShape(12.dp),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .background(MaterialTheme.colorScheme.surface)
                                            .padding(16.dp)
                                    ) {
                                        Text(
                                            text = "Kabupaten/Kota: ${item.namaKabupatenKota} (${item.kodeKabupatenKota})",
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = "Jumlah Penderita: ${item.jumlahPenderita} ${item.satuan}",
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.End,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Button(
                                                onClick = {
                                                    navController.navigate("edit/health/${item.id}")
                                                },
                                                shape = RoundedCornerShape(8.dp),
                                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                                            ) {
                                                Text(
                                                    text = "Edit",
                                                    style = MaterialTheme.typography.bodyMedium
                                                )
                                            }
                                            Button(
                                                onClick = {
                                                    viewModel.deleteData(item)
                                                    Toast.makeText(
                                                        context,
                                                        "Data berhasil dihapus!",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                    navController.navigate("health")
                                                },
                                                shape = RoundedCornerShape(8.dp),
                                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                                                modifier = Modifier.padding(start = 8.dp)
                                            ) {
                                                Text(
                                                    text = "Hapus",
                                                    color = Color.White,
                                                    style = MaterialTheme.typography.bodyMedium
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
