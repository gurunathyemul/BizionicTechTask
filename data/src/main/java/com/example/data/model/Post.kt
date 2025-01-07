package com.example.data.model

data class Post(
    val id: Int,
    val title: String,
    val body: String
)

data class PostList(
    val postList: List<Post>
)