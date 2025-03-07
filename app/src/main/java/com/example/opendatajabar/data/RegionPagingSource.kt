package com.example.opendatajabar.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState

class RegionPagingSource(
    private val apiService: OpenDataApiService
) : PagingSource<Int, RegionModelsNetworkModel>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RegionModelsNetworkModel> {
        val page = params.key ?: 1
        return try {
            Log.d("RegionPagingSource", "Fetching data for page: $page")
            val response = apiService.getRegion(page)
            Log.d("RegionPagingSource", "Response: $response")

            val data = response.data ?: emptyList()
            return LoadResult.Page(
                data = data,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (data.isEmpty()) null else page + 1
            )

        } catch (e: Exception) {
            Log.e("RegionPagingSource", "Error loading page $page: ${e.localizedMessage}", e)
            LoadResult.Error(e)
        }
    }


    override fun getRefreshKey(state: PagingState<Int, RegionModelsNetworkModel>): Int? {
        return state.anchorPosition?.let { anchor ->
            state.closestPageToPosition(anchor)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchor)?.nextKey?.minus(1)
        }
    }
}
