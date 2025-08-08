package com.example.recipeapp.dataLayer.data

data class UiState(
    val loading: Boolean = false,
    val recipes: List<Recipe> = emptyList(),
    val error: String? = null,
    val isSearch: Boolean = false
)
