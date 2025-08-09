package com.example.recipeapp.dataLayer.database

import android.content.Context
import androidx.room.Database
import com.example.recipeapp.dataLayer.dao.RecipeDao
import androidx.room.Room.databaseBuilder
import com.example.recipeapp.dataLayer.data.Recipe

@Database(entities =[Recipe::class] , version = 1)
abstract class RecipeDatabase {
    abstract fun recipeDao():RecipeDao
    companion object{
        @Volatile
        private val INSTANCE: RecipeDatabase?= null

        fun getDatabase(context: Context):RecipeDatabase{
            return INSTANCE?:synchronized(this){
                val instance= databaseBuilder(
                    context.applicationContext,
                    RecipeDatabase::class.java,
                    "recipe_database"
                ).build()
                INSTANCE=instance

                instance
            }
        }
    }
}