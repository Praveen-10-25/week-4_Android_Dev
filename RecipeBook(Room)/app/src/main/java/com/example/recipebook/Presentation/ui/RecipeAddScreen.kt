package com.example.recipeapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.recipebook.DataLayer.data.Recipe
import com.example.recipebook.Domain.ViewModel.RecipeViewModel
import kotlin.random.Random

@Composable
fun AddRecipeScreen(viewModel: RecipeViewModel, onDone: () -> Unit) {
    var title by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }
    var ingredientsText by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = imageUrl,
            onValueChange = { imageUrl = it },
            label = { Text("Image URL") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = ingredientsText,
            onValueChange = { ingredientsText = it },
            label = { Text("Ingredients (comma separated)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (error.isNotEmpty()) {
            Text(error, color = MaterialTheme.colorScheme.error)
            Spacer(modifier = Modifier.height(8.dp))
        }

        Button(
            onClick = {
                if (title.isBlank() || ingredientsText.isBlank()) {
                    error = "Please fill all fields"
                    return@Button
                }

                val ingredients = ingredientsText
                    .split(",")
                    .map { it.trim() }
                    .filter { it.isNotEmpty() }

                val id = Random.nextInt(1_000_000, Int.MAX_VALUE)

                val recipe = Recipe(
                    id = id,
                    title = title,
                    featuredImage = imageUrl,
                    ingredients = ingredients,
                    isLocal = true
                )

                viewModel.pushRecipeLocally(recipe)
                onDone()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Recipe")
        }
    }
}
