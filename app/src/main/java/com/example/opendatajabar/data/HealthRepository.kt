package com.example.opendatajabar.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HealthRepository @Inject constructor(
    private val apiService: OpenDataApiService,
    private val healthDao: HealthDao
) {

    fun getHealth(): Flow<PagingData<HealthEntity>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { HealthPagingSource(apiService) }
        ).flow.map { pagingData ->
            pagingData.map { net ->
                val entity = HealthEntity(
                    jumlahPenderita = net.jumlahPenderita,
                    kodeKabupatenKota = net.kodeKabupatenKota,
                    namaKabupatenKota = net.namaKabupatenKota,
                    satuan = net.satuan.toString(),
                    tahun = net.tahun
                )
                healthDao.insert(entity)
                entity
            }
        }
    }

    suspend fun getHealthById(id: Int): HealthEntity? {
        return healthDao.getById(id)
    }

}