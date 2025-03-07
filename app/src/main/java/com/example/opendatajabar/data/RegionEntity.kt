package com.example.opendatajabar.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "region_table")
data class RegionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val kodeProvinsi: Int,
    val namaProvinsi: String,
    val kodeKabupatenKota: Int,
    val namaKabupatenKota: String,
)
