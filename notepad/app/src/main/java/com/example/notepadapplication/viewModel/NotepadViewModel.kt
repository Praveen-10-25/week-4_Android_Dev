package com.example.notepadapplication.viewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.notepadapplication.data.Note

class NoteViewModel : ViewModel() {


    private val _notes = mutableStateListOf<Note>()
    val notes: List<Note> get() = _notes

    fun addNote(title: String, content: String) {
        val newNote = Note(id = _notes.size + 1, title = title, content = content)
        _notes.add(newNote)
    }

    fun deleteNote(note: Note) {
        _notes.remove(note)
    }
}