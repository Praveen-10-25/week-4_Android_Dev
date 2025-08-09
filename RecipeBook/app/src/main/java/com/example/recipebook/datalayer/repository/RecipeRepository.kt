package com.example.recipebook.datalayer.repository

import com.example.recipebook.datalayer.data.Recipe

interface RecipeRepository {
    suspend fun getRecipes(query: String): Result<List<Recipe>>
    suspend fun getDefaultRecipes(): Result<List<Recipe>>
    suspend fun getCachedRecipes(): Result<List<Recipe>>
    suspend fun saveRecipesToCache(recipes: List<Recipe>)
}
