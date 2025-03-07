package com.example.opendatajabar.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface RegionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: RegionEntity)

    @Update
    suspend fun update(data: RegionEntity)

    @Query("SELECT * FROM region_table ORDER BY id DESC")
    fun getAll(): Flow<List<RegionEntity>>

    @Query("SELECT * FROM region_table WHERE id = :dataId")
    suspend fun getById(dataId: Int): RegionEntity?

    @Delete
    suspend fun delete(data: RegionEntity)
}
