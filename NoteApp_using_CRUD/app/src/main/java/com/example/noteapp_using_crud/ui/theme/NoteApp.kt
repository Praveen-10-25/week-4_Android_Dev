package com.example.noteapp_using_crud.ui.theme

import NoteScreen
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.noteapp_using_crud.viewmodel.NoteViewModel

@Composable
fun NoteApp() {
    val noteViewModel: NoteViewModel = viewModel()
    NoteScreen(noteViewModel)
}
