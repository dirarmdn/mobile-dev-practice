package com.example.opendatajabar.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,
    val nim: String,
    val namaLengkap: String,
    val email: String,
    val imagePath: String? = null
)
