package com.example.day_02_api_fetching.repository

import com.example.day_02_api_fetching.data.Post
import com.example.day_02_api_fetching.networking.RF

class PostRepository {
    suspend fun getPosts(): List<Post> {
        return RF.api.getPosts()
    }
}
