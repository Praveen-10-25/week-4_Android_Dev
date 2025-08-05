package com.example.noteapp_using_crud.usecase

import com.example.noteapp_using_crud.data.Notes
import com.example.noteapp_using_crud.data.repository.NoteRepository


class getNoteUseCase(private val getNotesRepository: NoteRepository) {
    suspend fun invoke(noteId:Int): Notes? {
       return getNotesRepository.getNoteById(noteId)
}
}