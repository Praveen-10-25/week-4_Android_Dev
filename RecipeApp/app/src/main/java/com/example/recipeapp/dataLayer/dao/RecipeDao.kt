package com.example.recipeapp.dataLayer.dao


import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.recipeapp.dataLayer.data.Recipe

@Dao
interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: Recipe)

    @Delete
    suspend fun deleteRecipe(recipe: Recipe)

    @Query("SELECT * FROM recipe")
    fun getAllNotes(): LiveData<List<Recipe>>

    @Update
    suspend fun updateNote(recipe: Recipe)
}