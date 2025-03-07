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
class RegionRepository @Inject constructor(
    private val apiService: OpenDataApiService,
    private val regionDao: RegionDao
) {

    fun getRegion(): Flow<PagingData<RegionEntity>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { RegionPagingSource(apiService) }
        ).flow.map { pagingData ->
            pagingData.map { net ->
                val entity = RegionEntity(
                    kodeProvinsi = net.kodeProvinsi,
                    namaProvinsi = net.namaProvinsi,
                    kodeKabupatenKota = net.kodeKabupatenKota,
                    namaKabupatenKota = net.namaKabupatenKota
                )
                regionDao.insert(entity)
                entity
            }
        }
    }

    suspend fun getRegionById(id: Int): RegionEntity? {
        return regionDao.getById(id)
    }


}