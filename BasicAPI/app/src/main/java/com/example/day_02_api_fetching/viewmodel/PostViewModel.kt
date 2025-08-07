package com.example.day_02_api_fetching.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.day_02_api_fetching.data.Post
import com.example.day_02_api_fetching.repository.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
class PostViewModel : ViewModel() {
    private val repository = PostRepository()
    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> = _posts
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    init {
        fetchPosts()
    }
    private fun fetchPosts() {
        viewModelScope.launch {
            try {
                val result = repository.getPosts()
                _posts.value = result
                _errorMessage.value = null
            } catch (e: HttpException) {
                _errorMessage.value = "Server Error: ${e.code()} ${e.message()}"
            } catch (e: IOException) {
                _errorMessage.value = "Network Error: ${e.message ?: "No internet connection"}"
            } catch (e: Exception) {
                _errorMessage.value = "Unknown Error: ${e.localizedMessage}"
            }
        }
    }
}
