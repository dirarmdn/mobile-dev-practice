package com.example.opendatajabar.data

import retrofit2.http.GET
import retrofit2.http.Query

interface OpenDataApiService {
    @GET("bigdata/diskominfo/od_kode_wilayah_dan_nama_wilayah_kota_kabupaten")
    suspend fun getRegion(@Query("page") page: Int): RegionSearchResponse

    @GET("bigdata/dinkes//od_17448_jml_penderita_diabetes_melitus__kabupatenkota_v1")
    suspend fun getHealth(@Query("page") page: Int): HealthSearchResponse
}