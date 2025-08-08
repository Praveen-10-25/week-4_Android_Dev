package com.example.recipeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.example.recipeapp.dataLayer.data.repository.RecipeRepository
import com.example.recipeapp.domain.NavGraph
import com.example.recipeapp.domain.viewmodel.RecipeViewModel
import com.example.recipeapp.domain.viewmodel.RecipeViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = RecipeRepository()
        val factory = RecipeViewModelFactory(repository)
        val viewModel = ViewModelProvider(this, factory).get(RecipeViewModel::class.java)

        setContent {
            NavGraph(viewModel)
        }
    }
}
