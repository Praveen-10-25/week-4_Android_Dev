package com.example.recipebook.DataLayer.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Entity(tableName = "recipes")
@TypeConverters(Converters::class)
data class RecipeEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val title: String,
    val featured_image: String,
    val ingredients: List<String>,
    val isLocal: Boolean = false
)

class Converters {
    @TypeConverter
    fun fromList(list: List<String>): String = list.joinToString("||")

    @TypeConverter
    fun toList(data: String): List<String> =
        if (data.isEmpty()) emptyList() else data.split("||")
}
