package com.example.opendatajabar.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.opendatajabar.data.RegionDao
import com.example.opendatajabar.data.RegionEntity
import com.example.opendatajabar.data.RegionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RegionViewModel @Inject constructor(
    private val repository: RegionRepository,
    private val dao: RegionDao
) : ViewModel() {
    private val _regionData = MutableStateFlow<RegionEntity?>(null)
    val regionData: StateFlow<RegionEntity?> = _regionData

    val regionList: Flow<PagingData<RegionEntity>> = repository.getRegion()
        .cachedIn(viewModelScope)

    fun insertData(
        kodeProvinsi: Int,
        namaProvinsi: String,
        kodeKabupatenKota: Int,
        namaKabupatenKota: String,
    ) {
        viewModelScope.launch {
            dao.insert(
                RegionEntity(
                    kodeProvinsi = kodeProvinsi,
                    namaProvinsi = namaProvinsi,
                    kodeKabupatenKota = kodeKabupatenKota,
                    namaKabupatenKota = namaKabupatenKota
                )
            )
        }
    }

    fun updateData(data: RegionEntity) {
        viewModelScope.launch {
            dao.update(data)
        }
    }

    fun fetchRegionById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val data = dao.getById(id)
            withContext(Dispatchers.Main) {
                _regionData.value = data
            }
        }
    }


    fun deleteData(data: RegionEntity) {
        viewModelScope.launch {
            dao.delete(data)
        }
    }
}
