package com.example.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.model.Post
import com.example.data.remote.ApiService
import retrofit2.HttpException
import java.io.IOException

class PostPagingSource(private val apiService: ApiService) : PagingSource<Int, Post>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Post> {
        val page = params.key ?: 1 // Default page = 1
        return try {
            val response = apiService.getPosts(page, params.loadSize)
            LoadResult.Page(
                data = response,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.isEmpty()) null else page + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e) // Handle network errors
        } catch (e: HttpException) {
            LoadResult.Error(e) // Handle HTTP errors
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Post>): Int? {
        // Return the key for the closest page to the anchor position
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
