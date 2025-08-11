package com.example.recipebook.datalayer.repository

import androidx.lifecycle.LiveData
import com.example.recipebook.domain.Api
import com.example.recipebook.datalayer.dao.RecipeDao
import com.example.recipebook.datalayer.data.Recipe
import com.example.recipebook.datalayer.data.RecipeEntity
import com.example.recipebook.datalayer.mapper.toDomain
import com.example.recipebook.datalayer.mapper.toEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipeRepositoryImpl @Inject constructor(
    private val api: Api,
    private val dao: RecipeDao
) : RecipeRepository {

    override suspend fun getRecipes(query: String): Result<List<Recipe>> {
        return withContext(Dispatchers.IO) {
            try {
                val resp = api.getRecipes(query)
                val recipes = resp.results
                dao.insertRecipes(recipes.map { it.toEntity() })
                Result.success(recipes)
            } catch (t: Throwable) {
                val cached = dao.getAllRecipes().map { it.toDomain() }
                if (cached.isNotEmpty()) Result.success(cached) else Result.failure(t)
            }
        }
    }
    override fun getRecipeById(id: Int): LiveData<RecipeEntity> {
        return dao.getRecipeById(id)
    }


    override suspend fun getDefaultRecipes(): Result<List<Recipe>> {
        return withContext(Dispatchers.IO) {
            try {
                val resp = api.getDefaultRecipes()
                val recipes = resp.results
                dao.insertRecipes(recipes.map { it.toEntity() })
                Result.success(recipes)
            } catch (t: Throwable) {
                val cached = dao.getAllRecipes().map { it.toDomain() }
                if (cached.isNotEmpty()) Result.success(cached) else Result.failure(t)
            }
        }
    }

    override suspend fun getCachedRecipes(): Result<List<Recipe>> {
        return withContext(Dispatchers.IO) {
            try {
                val cached = dao.getAllRecipes().map { it.toDomain() }
                Result.success(cached)
            } catch (t: Throwable) {
                Result.failure(t)
            }
        }
    }

    override suspend fun saveRecipesToCache(recipes: List<Recipe>) {
        withContext(Dispatchers.IO) {
            dao.insertRecipes(recipes.map { it.toEntity() })
        }
    }
}
