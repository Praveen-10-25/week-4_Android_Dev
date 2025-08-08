package com.example.recipeapp.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.recipeapp.domain.viewmodel.RecipeViewModel

@Composable
fun RecipeDetailScreen(recipeId: Int, viewModel: RecipeViewModel) {
    val recipe = viewModel.recipes.observeAsState().value?.find { it.id == recipeId }

    recipe?.let {

        Column(modifier = Modifier.padding(16.dp).verticalScroll(rememberScrollState()))
        {   Card(
            elevation = CardDefaults.cardElevation(8.dp),
            modifier = Modifier.fillMaxWidth()
        ){
            AsyncImage(
                model = it.featured_image,
                contentDescription = it.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                contentScale = ContentScale.Crop
            )
        }
            Spacer(modifier = Modifier.height(12.dp))
            Card(
                elevation = CardDefaults.cardElevation(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Title: ${it.title}", style = MaterialTheme.typography.headlineSmall)
            }
                Spacer(modifier = Modifier.height(12.dp))

            Card(
                elevation = CardDefaults.cardElevation(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Ingredients:",
                        style = MaterialTheme.typography.headlineSmall
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    for (ingredient in it.ingredients) {
                        Text(
                            text = "• $ingredient",
                            fontSize = 20.sp
                        )
                        Spacer(modifier = Modifier.height(9.dp))
                    }
                }
            }

        }
    }?: run {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("Recipe not found.")
        }
    }
}

