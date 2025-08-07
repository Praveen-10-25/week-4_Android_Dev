package com.example.day_02_api_fetching.networking

import com.example.day_02_api_fetching.data.Post
import retrofit2.http.GET

interface Api {
    @GET("/posts")
    suspend fun getPosts():List<Post>
}