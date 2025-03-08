package com.example.opendatajabar.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.opendatajabar.data.HealthDao
import com.example.opendatajabar.data.HealthEntity
import com.example.opendatajabar.data.HealthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HealthViewModel @Inject constructor(
    private val repository: HealthRepository,
    private val dao: HealthDao
) : ViewModel() {
    private val _healthData = MutableStateFlow<HealthEntity?>(null)
    val healthData: StateFlow<HealthEntity?> = _healthData

    val healthList: Flow<PagingData<HealthEntity>> = repository.getHealth()
        .cachedIn(viewModelScope)

    fun insertData(
        jumlahPenderita: Int,
        satuan: String,
        tahun: Int,
        kodeKabupatenKota: Int,
        namaKabupatenKota: String,
    ) {
        viewModelScope.launch {
            dao.insert(
                HealthEntity(
                    jumlahPenderita = jumlahPenderita,
                    satuan = satuan,
                    kodeKabupatenKota = kodeKabupatenKota,
                    namaKabupatenKota = namaKabupatenKota,
                    tahun = tahun
                )
            )
        }
    }

    fun updateData(data: HealthEntity) {
        viewModelScope.launch {
            dao.update(data)
        }
    }

    fun fetchHealthById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val data = dao.getById(id)
            withContext(Dispatchers.Main) {
                _healthData.value = data
            }
        }
    }


    fun deleteData(data: HealthEntity) {
        viewModelScope.launch {
            dao.delete(data)
        }
    }
}
