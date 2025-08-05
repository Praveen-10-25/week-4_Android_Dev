package com.example.noteapp_using_crud.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.noteapp_using_crud.data.Notes

@Dao
interface NoteDao {
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insertNote(note: Notes)

        @Delete
        suspend fun deleteNote(note: Notes)

        @Query("SELECT * FROM notes ORDER BY id DESC")
        fun getAllNotes(): LiveData<List<Notes>>

        @Query("SELECT * FROM notes WHERE id = :id")
        suspend fun getNoteById(id: Int): Notes?

        @Update
        suspend fun updateNote(note: Notes)
}
