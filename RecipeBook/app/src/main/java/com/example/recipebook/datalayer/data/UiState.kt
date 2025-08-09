package com.example.recipebook.datalayer.data

data class UiState(
    val loading: Boolean = false,
    val recipes: List<Recipe> = emptyList(),
    val error: String? = null
)
