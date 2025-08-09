package com.example.recipeapp.domain.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.dataLayer.data.Recipe
import com.example.recipeapp.dataLayer.data.UiState
import com.example.recipeapp.dataLayer.repository.RecipeRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RecipeViewModel(private val repository: RecipeRepository) : ViewModel() {


    private val _recipes = MutableLiveData<List<Recipe>>(emptyList())
    val recipes: LiveData<List<Recipe>> = _recipes

    private val _error = MutableLiveData<String?>(null)
    val error: LiveData<String?> = _error


    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    fun fetchRecipes(query: String) {

        _uiState.value = _uiState.value.copy(loading = true, error = null)

        viewModelScope.launch {
            val result = repository.getRecipes(query)

            delay(2000)
            result.onSuccess { recipeList ->

                _recipes.value = recipeList
                _error.value = null


                _uiState.value = UiState(
                    loading = false,
                    recipes = recipeList,
                    error = null
                )
            }.onFailure { throwable ->

                _recipes.value = emptyList()
                _error.value = throwable.message


                _uiState.value = UiState(
                    loading = false,
                    recipes = emptyList(),
                    error = throwable.message
                )
            }
        }
    }
    fun fetchDefaultRecipes() {
        _uiState.value = _uiState.value.copy(loading = true, error = null)

        viewModelScope.launch {
            val result = repository.getDefaultRecipes()
            result.onSuccess { recipeList ->
                _recipes.value = recipeList
                _error.value = null
                _uiState.value = _uiState.value.copy(loading = false, recipes = recipeList)
            }.onFailure { throwable ->
                _recipes.value = emptyList()
                _error.value = throwable.message
                _uiState.value = _uiState.value.copy(loading = false, error = throwable.message)
            }
        }
    }

}
