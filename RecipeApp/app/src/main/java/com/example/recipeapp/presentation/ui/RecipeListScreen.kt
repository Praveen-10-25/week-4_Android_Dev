package com.example.recipeapp.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.recipeapp.domain.viewmodel.RecipeViewModel


@Composable
fun RecipeScreen(viewModel: RecipeViewModel = viewModel()) {
    val recipes by viewModel.recipes.observeAsState(emptyList())
    val error by viewModel.error.observeAsState()

    var searchQuery by remember { mutableStateOf("") }

    Column {
        TextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                viewModel.fetchRecipes(it)
            },
            label = { Text("Search for a recipe") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        if (error != null) {
            Text("Error: $error", color = Color.Red, modifier = Modifier.padding(16.dp))
        }

        LazyColumn {
            items(recipes) { recipe ->
                Text(
                    text = recipe.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
            }
        }
    }
}
