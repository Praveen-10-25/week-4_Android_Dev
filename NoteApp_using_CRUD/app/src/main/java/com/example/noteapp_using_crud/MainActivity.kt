package com.example.noteapp_using_crud

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.example.noteapp_using_crud.data.db.NoteDatabase
import com.example.noteapp_using_crud.data.repository.NoteRepository
import com.example.noteapp_using_crud.ui.NoteScreen
import com.example.noteapp_using_crud.viewmodel.NoteViewModel
import com.example.noteapp_using_crud.viewmodel.NoteViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dao = NoteDatabase.getDatabase(this).noteDao()
        val repository = NoteRepository(dao)
        val viewModelFactory = NoteViewModelFactory(repository)
        val viewModel = ViewModelProvider(this, viewModelFactory)[NoteViewModel::class.java]

        setContent {
            NoteScreen(viewModel = viewModel)
        }
    }
}
