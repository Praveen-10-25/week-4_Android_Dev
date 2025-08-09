package com.example.recipebook.datalayer.mapper

import com.example.recipebook.datalayer.data.Recipe
import com.example.recipebook.datalayer.data.RecipeEntity

fun Recipe.toEntity(): RecipeEntity {
    return RecipeEntity(
        id = id,
        title = title,
        featuredImage = featured_image,
        ingredients = ingredients
    )
}

fun RecipeEntity.toDomain(): Recipe {
    return Recipe(
        id = id,
        title = title,
        featured_image = featuredImage,
        ingredients = ingredients
    )
}
