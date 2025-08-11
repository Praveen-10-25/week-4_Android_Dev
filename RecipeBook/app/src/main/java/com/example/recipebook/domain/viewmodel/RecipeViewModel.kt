package com.example.recipebook.domain.viewmodel

import androidx.lifecycle.*
import com.example.recipebook.datalayer.data.Recipe
import com.example.recipebook.datalayer.data.RecipeEntity
import com.example.recipebook.datalayer.data.UiState
import com.example.recipebook.datalayer.repository.LocalRecipeRepository
import com.example.recipebook.datalayer.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val remoteRepository: RecipeRepository,
    private val localRepository: LocalRecipeRepository
) : ViewModel() {

    private val _recipes = MutableLiveData<List<Recipe>>(emptyList())
    val recipes: LiveData<List<Recipe>> = _recipes

    private val _error = MutableLiveData<String?>(null)
    val error: LiveData<String?> = _error

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState


    val localRecipes: LiveData<List<RecipeEntity>> = localRepository.getAllRecipesLive()

    fun fetchRecipes(query: String) {
        _uiState.value = _uiState.value.copy(loading = true, error = null)
        viewModelScope.launch {
            val result = remoteRepository.getRecipes(query)
            delay(200)
            result.onSuccess { recipeList ->
                _recipes.value = recipeList
                _error.value = null
                _uiState.value = UiState(loading = false, recipes = recipeList, error = null)
            }.onFailure { throwable ->
                _recipes.value = emptyList()
                _error.value = throwable.message
                _uiState.value = UiState(loading = false, recipes = emptyList(), error = throwable.message)
            }
        }
    }

    fun fetchDefaultRecipes() {
        _uiState.value = _uiState.value.copy(loading = true, error = null)
        viewModelScope.launch {
            val result = remoteRepository.getDefaultRecipes()
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


    fun addLocalRecipe(title: String, ingredients: List<String>, imageUrl: String) {
        viewModelScope.launch {
            val recipeEntity = RecipeEntity(
                id = (System.currentTimeMillis() / 1000).toInt(),
                title = title,
                featuredImage = imageUrl,
                ingredients = ingredients
            )
            localRepository.insertRecipe(recipeEntity)
        }
    }

    fun updateLocalRecipe(id: Int, title: String, ingredients: List<String>, imageUrl: String) {
        viewModelScope.launch {
            val updatedRecipe = RecipeEntity(
                id = id,
                title = title,
                featuredImage = imageUrl,
                ingredients = ingredients
            )
            localRepository.updateRecipe(updatedRecipe)
        }
    }

    fun getRecipeById(id: Int): LiveData<RecipeEntity> {
        return remoteRepository.getRecipeById(id)
    }

    fun deleteLocalRecipe(recipe: RecipeEntity) {
        viewModelScope.launch {
            localRepository.deleteRecipe(recipe)
        }
    }
    fun fetchRecipesAndStore(query: String) {
        _uiState.value = _uiState.value.copy(loading = true, error = null)
        viewModelScope.launch {
            val result = remoteRepository.getRecipes(query)
            delay(200)
            result.onSuccess { recipeList ->
                _recipes.value = recipeList
                _error.value = null
                _uiState.value = UiState(loading = false, recipes = recipeList, error = null)

                val entities = recipeList.map { recipe ->
                    RecipeEntity(
                        id = recipe.id,
                        title = recipe.title,
                        featuredImage = recipe.featured_image,
                        ingredients = recipe.ingredients
                    )
                }

                localRepository.insertRecipes(entities)

            }.onFailure { throwable ->
                _recipes.value = emptyList()
                _error.value = throwable.message
                _uiState.value = UiState(loading = false, recipes = emptyList(), error = throwable.message)
            }
        }
    }

}
