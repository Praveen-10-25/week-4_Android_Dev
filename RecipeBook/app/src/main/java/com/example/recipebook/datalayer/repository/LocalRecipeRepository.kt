package com.example.recipebook.datalayer.repository

import androidx.lifecycle.LiveData
import com.example.recipebook.datalayer.dao.RecipeDao
import com.example.recipebook.datalayer.data.RecipeEntity
import javax.inject.Inject

class LocalRecipeRepository @Inject constructor(
    private val recipeDao: RecipeDao
) {
    suspend fun insertRecipe(recipe: RecipeEntity) = recipeDao.insertRecipe(recipe)
    suspend fun insertRecipes(recipes: List<RecipeEntity>) = recipeDao.insertRecipes(recipes)
    suspend fun deleteRecipe(recipe: RecipeEntity) = recipeDao.deleteRecipe(recipe)
    fun getAllRecipesLive(): LiveData<List<RecipeEntity>> = recipeDao.getAllRecipesLive()
    suspend fun getAllRecipes(): List<RecipeEntity> = recipeDao.getAllRecipes()
    fun getRecipeById(id: Int): LiveData<RecipeEntity> {
        return recipeDao.getRecipeById(id)
    }
    suspend fun updateRecipe(recipe: RecipeEntity) = recipeDao.updateRecipe(recipe)
}
