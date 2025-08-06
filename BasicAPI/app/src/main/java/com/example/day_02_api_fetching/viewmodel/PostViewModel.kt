package com.example.day_02_api_fetching.viewmodel

import androidx.lifecycle.*
import com.example.day_02_api_fetching.data.Post
import com.example.day_02_api_fetching.networking.RF
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PostViewModel : ViewModel() {

    private val _posts = MutableLiveData<List<Post>>()
    val posts: LiveData<List<Post>> = _posts

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    init {
        fetchPostsWithDelay()
    }

    private fun fetchPostsWithDelay() {
        viewModelScope.launch {
            try {
                delay(2000)

                val deferredPosts = async {
                    RF.api.getPosts()
                }

                val result = deferredPosts.await()
                _posts.value = result
                _errorMessage.value = null

            } catch (e: Exception) {
                _posts.value = emptyList()
                _errorMessage.value = "Failed to load posts: ${e.message}"
            }
        }
    }
}
