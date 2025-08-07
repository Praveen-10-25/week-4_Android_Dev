package com.example.day_02_api_fetching.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.day_02_api_fetching.data.Post
import com.example.day_02_api_fetching.data.UserState
import com.example.day_02_api_fetching.repository.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class PostViewModel : ViewModel() {

    private val repository = PostRepository()

    private val _postState = MutableStateFlow<UserState<List<Post>>>(UserState.Loading)
    val postState: StateFlow<UserState<List<Post>>> = _postState

    init {
        fetchPosts()
    }

    private fun fetchPosts() {
        viewModelScope.launch {
            _postState.value = UserState.Loading
            try {
                val result = repository.getPosts()
                _postState.value = UserState.Success(result)
            } catch (e: HttpException) {
                _postState.value = UserState.Error("Server Error: ${e.code()} ${e.message()}")
            } catch (e: IOException) {
                _postState.value = UserState.Error("Network Error: ${e.message ?: "No internet connection"}")
            } catch (e: Exception) {
                _postState.value = UserState.Error("Unknown Error: ${e.localizedMessage}")
            }
        }
    }
}
