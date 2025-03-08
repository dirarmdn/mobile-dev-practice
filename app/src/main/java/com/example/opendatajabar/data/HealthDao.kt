package com.example.opendatajabar.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface HealthDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: HealthEntity)

    @Update
    suspend fun update(data: HealthEntity)

    @Query("SELECT * FROM diabetic_table ORDER BY id DESC")
    fun getAll(): Flow<List<HealthEntity>>

    @Query("SELECT * FROM diabetic_table WHERE id = :dataId")
    suspend fun getById(dataId: Int): HealthEntity?

    @Delete
    suspend fun delete(data: HealthEntity)
}