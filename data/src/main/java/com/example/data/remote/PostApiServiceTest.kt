package com.example.data.remote

import com.example.data.model.Post
import retrofit2.http.GET

interface PostApiServiceTest {
    @GET("posts")
    suspend fun getListPosts(): List<Post>
}
