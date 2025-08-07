package com.example.day_02_api_fetching.data


sealed class UserState<out T> {
    object Loading : UserState<Nothing>()
    data class Success<T>(val data: T) : UserState<T>()
    data class Error(val message: String) : UserState<Nothing>()
}
