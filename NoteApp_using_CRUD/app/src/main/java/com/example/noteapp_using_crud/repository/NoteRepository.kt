package com.example.noteapp_using_crud.repository

import com.example.noteapp_using_crud.dao.NoteDao
import com.example.noteapp_using_crud.data.Notes

class NoteRepository(private val noteDao: NoteDao) {
    val allNotes = noteDao.getAllNotes()

    suspend fun insertNote(note: Notes) = noteDao.insertNote(note)
    suspend fun deleteNote(note: Notes) = noteDao.deleteNote(note)
    suspend fun getNoteById(id: Int) = noteDao.getNoteById(id)
}
