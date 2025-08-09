package com.example.recipeapp.dataLayer.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Recipe")
data class RecipeEntity (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo("id")
    val id:Int,
    @ColumnInfo("title")val title:String,
    @ColumnInfo("featuredImage")val featuredImage:String,
    @ColumnInfo("ingredients")val ingredients:List<String>,
)