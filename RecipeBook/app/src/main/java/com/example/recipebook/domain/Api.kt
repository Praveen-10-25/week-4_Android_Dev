package com.example.recipebook.domain

import com.example.recipebook.datalayer.data.RecipeResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface Api {
    @GET("api/recipe/search/")
    suspend fun getRecipes(
        @Query("query") query: String,
        @Query("page") page: Int = 1,
        @Header("Authorization") token: String = "Token 9c8b06d329136da358c2d00e76946b0111ce2c48"
    ): RecipeResponse

    @GET("api/recipe/search/")
    suspend fun getDefaultRecipes(
        @Query("query") query: String = "chicken",
        @Query("page") page: Int = 1,
        @Header("Authorization") token: String = "Token 9c8b06d329136da358c2d00e76946b0111ce2c48"
    ): RecipeResponse
}
