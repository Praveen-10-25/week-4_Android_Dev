package com.example.recipebook.datalayer.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.recipebook.datalayer.dao.RecipeDao
import com.example.recipebook.datalayer.data.Converters
import com.example.recipebook.datalayer.data.RecipeEntity

@Database(entities = [RecipeEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
}
