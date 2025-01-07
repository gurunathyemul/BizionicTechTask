package com.example.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.data.remote.ApiService

class PostRepository(private val apiService: ApiService) {

    fun getPagedPosts() = Pager(
        config = PagingConfig(
            pageSize = 10, // Number of items per page
            enablePlaceholders = false
        ),
        pagingSourceFactory = { PostPagingSource(apiService) }
    ).liveData
}
