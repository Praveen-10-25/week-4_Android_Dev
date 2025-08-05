package com.example.noteapp_using_crud.viewmodel

import androidx.lifecycle.*
import com.example.noteapp_using_crud.data.Notes
import com.example.noteapp_using_crud.data.repository.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(private val repository: NoteRepository) : ViewModel() {

    private val _title = MutableLiveData("")
    val title: LiveData<String> = _title

    private val _content = MutableLiveData("")
    val content: LiveData<String> = _content

    fun onTitleChange(newTitle: String) {
        _title.value = newTitle
    }

    fun onContentChange(newContent: String) {
        _content.value = newContent
    }

    val notes: LiveData<List<Notes>> = repository.allNotes

    fun addNote() = viewModelScope.launch {
        val currentTitle = _title.value ?: ""
        val currentContent = _content.value ?: ""

        if (currentTitle.isNotBlank() && currentContent.isNotBlank()) {
            repository.insertNote(Notes(title = currentTitle, content = currentContent))
            _title.value = ""
            _content.value = ""
        }
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
