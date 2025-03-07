package com.example.opendatajabar.data

import com.google.gson.annotations.SerializedName

data class RegionSearchResponse(
    @SerializedName("data") val data: List<RegionModelsNetworkModel>
)

data class RegionModelsNetworkModel(
    @SerializedName("id") val id: Int,
    @SerializedName("bps_kota_kode") val kodeKabupatenKota: Int,
    @SerializedName("bps_kota_nama") val namaKabupatenKota: String,
    @SerializedName("bps_provinsi_kode") val kodeProvinsi: Int,
    @SerializedName("bps_provinsi_nama") val namaProvinsi: String
)