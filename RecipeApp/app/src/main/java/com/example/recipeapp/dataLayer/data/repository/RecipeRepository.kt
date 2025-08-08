package com.example.recipeapp.dataLayer.data.repository

import com.example.recipeapp.dataLayer.data.Recipe
import com.example.recipeapp.domain.RetroFitInstance.api
import retrofit2.HttpException
import java.io.IOException

class RecipeRepository {
val neterror: String=" Turn on Internet "
    suspend fun getRecipes(query: String): Result<List<Recipe>> {
        return try {
            val response = api.getRecipes(query)
            Result.success(response.results)
        } catch (e: HttpException) {
            Result.failure(Exception("HTTP ${e.code()}:"))
        } catch (e: IOException) {
            Result.failure(Exception("Network error: ${neterror}"))
        } catch (e: Exception) {
            Result.failure(Exception("Unexpected error: ${e.message}"))
        }
    }

    suspend fun getDefaultRecipes(): Result<List<Recipe>> {
        return try {
            val response = api.getDefaultRecipes()
            Result.success(response.results)
        } catch (e: HttpException) {
            Result.failure(Exception("HTTP ${e.code()}:"))
        } catch (e: IOException) {
            Result.failure(Exception("Network error: ${neterror}"))
        } catch (e: Exception) {
            Result.failure(Exception("Unexpected error: ${e.message}"))
        }
    }
}
