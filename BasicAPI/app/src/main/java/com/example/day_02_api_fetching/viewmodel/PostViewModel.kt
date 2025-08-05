package com.example.day_02_api_fetching.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.day_02_api_fetching.data.Post
import com.example.day_02_api_fetching.networking.RF
import kotlinx.coroutines.launch

class PostViewModel: ViewModel() {

    private val _posts= MutableLiveData<List<Post>>()
    val posts : LiveData<List<Post>> = _posts

    init{
        fetch()
    }
    private fun fetch(){
        viewModelScope.launch {
            try{
                val result = RF.api.getPosts()
                _posts.value=result
            }
            catch (e:Exception){
                _posts.value=emptyList()
            }
        }
    }
}