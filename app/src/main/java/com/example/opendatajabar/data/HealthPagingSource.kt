package com.example.opendatajabar.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState

class HealthPagingSource(
    private val apiService: OpenDataApiService
) : PagingSource<Int, HealthSearchNetworkModel>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, HealthSearchNetworkModel> {
        val page = params.key ?: 1
        return try {
            Log.d("HealthPagingSource", "Fetching data for page: $page")
            val response = apiService.getHealth(page)
            Log.d("HealthPagingSource", "Response: $response")

            val data = response.data ?: emptyList()
            return LoadResult.Page(
                data = data,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (data.isEmpty()) null else page + 1
            )

        } catch (e: Exception) {
            Log.e("HealthPagingSource", "Error loading page $page: ${e.localizedMessage}", e)
            LoadResult.Error(e)
        }
    }


    override fun getRefreshKey(state: PagingState<Int, HealthSearchNetworkModel>): Int? {
        return state.anchorPosition?.let { anchor ->
            state.closestPageToPosition(anchor)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchor)?.nextKey?.minus(1)
        }
    }
}
