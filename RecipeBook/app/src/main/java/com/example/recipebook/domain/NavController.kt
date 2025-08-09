package com.example.recipebook.domain

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.recipebook.domain.viewmodel.RecipeViewModel
import com.example.recipebook.presentation.ui.LocalRecipeDetailScreen
import com.example.recipebook.presentation.ui.LocalRecipeListScreen
import com.example.recipebook.presentation.ui.RecipeDetailScreen
import com.example.recipebook.presentation.ui.RecipeListScreen

@Composable
fun NavGraph(viewModel: RecipeViewModel) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "list") {
        composable("list") {
            RecipeListScreen(navController)
        }
        composable(
            "ingredients/{recipeId}",
            arguments = listOf(navArgument("recipeId") { type = NavType.IntType })
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getInt("recipeId") ?: -1
            RecipeDetailScreen(recipeId, navController)
        }
        composable("localList") {
            LocalRecipeListScreen(navController, viewModel)
        }
        composable(
            "localDetail/{localId}",
            arguments = listOf(navArgument("localId") { type = NavType.IntType })
        ) { backStackEntry ->
            val localId = backStackEntry.arguments?.getInt("localId") ?: -1
            LocalRecipeDetailScreen(localId, viewModel)
        }
    }
}
