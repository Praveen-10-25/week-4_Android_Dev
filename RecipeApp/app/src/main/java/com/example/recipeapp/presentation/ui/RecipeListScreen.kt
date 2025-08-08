package com.example.recipeapp.presentation.ui

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.example.recipeapp.domain.viewmodel.RecipeViewModel

@Composable
fun RecipeListScreen(navController: NavController, viewModel: RecipeViewModel) {
    val state by viewModel.uiState.collectAsState()
    val recipes by viewModel.recipes.observeAsState(emptyList())
    val error by viewModel.error.observeAsState()

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.fetchDefaultRecipes()
    }

    LaunchedEffect(recipes, state.loading) {
        if (!state.loading && recipes.isEmpty() && error == null) {
            Toast.makeText(context, "Dish not available", Toast.LENGTH_SHORT).show()
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        var query by remember { mutableStateOf("") }

        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Enter Dish") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { viewModel.fetchRecipes(query) },
            enabled = !state.loading
        ) {
            Text("Search")
        }

        Spacer(modifier = Modifier.height(16.dp))

        when {
            state.loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            error != null -> {
                Text(
                    "Error: $error",
                    color = MaterialTheme.colorScheme.error
                )
            }
            else -> {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(recipes) { recipe ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navController.navigate("ingredients/${recipe.id}")
                                },
                            elevation = CardDefaults.cardElevation(6.dp),
                            shape = MaterialTheme.shapes.medium
                        ) {
                            Column {
                                var isImageLoading by remember { mutableStateOf(true) }

                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(180.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    AsyncImage(
                                        model = recipe.featured_image,
                                        contentDescription = recipe.title,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize(),
                                        onState = { state ->
                                            isImageLoading = state is AsyncImagePainter.State.Loading
                                        }
                                    )

                                    if (isImageLoading) {
                                        CircularProgressIndicator()
                                    }
                                }

                                Text(
                                    text = recipe.title,
                                    style = MaterialTheme.typography.titleLarge,
                                    modifier = Modifier.padding(12.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
