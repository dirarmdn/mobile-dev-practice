package com.example.opendatajabar.data

import retrofit2.http.GET
import retrofit2.http.Query

interface OpenDataApiService {
    @GET("bigdata/diskominfo/od_kode_wilayah_dan_nama_wilayah_kota_kabupaten")
    suspend fun getRegion(@Query("page") page: Int): RegionSearchResponse
}