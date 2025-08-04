package com.example.noteapp_using_crud.viewmodel

import androidx.lifecycle.*
import com.example.noteapp_using_crud.data.Notes
import com.example.noteapp_using_crud.repository.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(private val repository: NoteRepository) : ViewModel() {
    val notes = repository.allNotes

    fun addNote(note: Notes) = viewModelScope.launch {
        repository.insertNote(note)
    }

    fun deleteNote(note: Notes) = viewModelScope.launch {
        repository.deleteNote(note)
    }

    fun getNoteById(id: Int): LiveData<Notes?> {
        val result = MutableLiveData<Notes?>()
        viewModelScope.launch {
            result.postValue(repository.getNoteById(id))
        }
        return result
    }
}
