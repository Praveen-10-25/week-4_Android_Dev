package com.example.recipeapp.dataLayer.data


data class Recipe(
    val id: Int,
    val title: String,
    val featured_image: String,
    val ingredients: List<String>
)

data class RecipeResponse(
    val results: List<Recipe>
)


