package com.example.recipebook.datalayer.repository

import androidx.lifecycle.LiveData
import com.example.recipebook.datalayer.data.Recipe
import com.example.recipebook.datalayer.data.RecipeEntity

interface RecipeRepository {
    suspend fun getRecipes(query: String): Result<List<Recipe>>
    suspend fun getDefaultRecipes(): Result<List<Recipe>>
    suspend fun getCachedRecipes(): Result<List<Recipe>>
    suspend fun saveRecipesToCache(recipes: List<Recipe>)
    fun getRecipeById(id: Int): LiveData<RecipeEntity>

}
