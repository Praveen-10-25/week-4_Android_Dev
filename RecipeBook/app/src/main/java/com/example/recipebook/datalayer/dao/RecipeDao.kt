package com.example.recipebook.datalayer.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.recipebook.datalayer.data.Converters
import com.example.recipebook.datalayer.data.RecipeEntity

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipes: List<RecipeEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: RecipeEntity)

    @Delete
    suspend fun deleteRecipe(recipe: RecipeEntity)

    @Query("SELECT * FROM recipes")
    fun getAllRecipesLive(): LiveData<List<RecipeEntity>>

    @Query("SELECT * FROM recipes")
    suspend fun getAllRecipes(): List<RecipeEntity>

    @Query("SELECT * FROM recipes WHERE id = :id LIMIT 1")
    fun getRecipeById(id: Int): LiveData<RecipeEntity>

    @Update
    suspend fun updateRecipe(recipe: RecipeEntity)
}
