package com.example.opendatajabar.data

import com.google.gson.annotations.SerializedName

data class HealthSearchResponse(
    @SerializedName("data") val data: List<HealthSearchNetworkModel>
)

data class HealthSearchNetworkModel(
    @SerializedName("id") val id: Int,
    @SerializedName("jumlah_penderita_dm") val jumlahPenderita: Int,
    @SerializedName("kode_kabupaten_kota") val kodeKabupatenKota: Int,
    @SerializedName("nama_kabupaten_kota") val namaKabupatenKota: String,
    @SerializedName("satuan") val satuan: String,
    @SerializedName("tahun") val tahun: Int
)