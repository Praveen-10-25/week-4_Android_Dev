package com.example.day_02_api_fetching.networking

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RF {
    val api: Api by lazy {
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
    }
}