package com.example.recipeapp.domain

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.recipeapp.domain.viewmodel.RecipeViewModel
import com.example.recipeapp.presentation.ui.RecipeDetailScreen
import com.example.recipeapp.presentation.ui.RecipeListScreen

@Composable
fun NavGraph(viewModel: RecipeViewModel) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "list") {
        composable("list") {
            RecipeListScreen(navController, viewModel)
        }
        composable(
            "ingredients/{recipeId}",
            arguments = listOf(navArgument("recipeId") { type = NavType.IntType })
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getInt("recipeId") ?: -1
            RecipeDetailScreen(recipeId, viewModel)
        }
    }
}

