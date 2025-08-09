package com.example.recipebook.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.recipebook.domain.viewmodel.RecipeViewModel

@Composable
fun LocalRecipeDetailScreen(
    localId: Int,
    viewModel: RecipeViewModel
) {
    val localRecipes = viewModel.localRecipes.observeAsState(emptyList()).value
    val recipe = localRecipes.find { it.id == localId }

    var isEditing by remember { mutableStateOf(false) }
    var editTitle by remember { mutableStateOf(recipe?.title ?: "") }
    var editIngredients by remember { mutableStateOf(recipe?.ingredients?.joinToString("\n") ?: "") }

    recipe?.let {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            AsyncImage(
                model = it.featuredImage,
                contentDescription = it.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (isEditing) {
                OutlinedTextField(
                    value = editTitle,
                    onValueChange = { editTitle = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = editIngredients,
                    onValueChange = { editIngredients = it },
                    label = { Text("Ingredients (one per line)") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                )
            } else {
                Text("Title: ${it.title}", style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(12.dp))

                Text("Ingredients:", style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(8.dp))
                it.ingredients.forEach { ingredient ->
                    Text(text = "• $ingredient", fontSize = 20.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { viewModel.deleteLocalRecipe(it) },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Delete")
                }

                if (isEditing) {
                    Button(onClick = {
                        val updatedIngredients = editIngredients
                            .split("\n")
                            .map { ing -> ing.trim() }
                            .filter { ing -> ing.isNotEmpty() }

                        viewModel.updateLocalRecipe(
                            id = it.id,
                            title = editTitle,
                            ingredients = updatedIngredients,
                            imageUrl = it.featuredImage
                        )
                        isEditing = false
                    }) {
                        Text("Save")
                    }
                } else {
                    Button(onClick = { isEditing = true }) {
                        Text("Edit")
                    }
                }
            }
        }
    } ?: Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Recipe not found")
    }
}
