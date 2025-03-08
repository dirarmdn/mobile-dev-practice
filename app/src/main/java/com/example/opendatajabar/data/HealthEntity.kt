package com.example.opendatajabar.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "diabetic_table")
data class HealthEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val jumlahPenderita: Int,
    val satuan: String,
    val kodeKabupatenKota: Int,
    val namaKabupatenKota: String,
    val tahun: Int,
)
