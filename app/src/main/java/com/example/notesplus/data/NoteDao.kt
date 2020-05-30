package com.example.notesplus.data

import androidx.room.*

@Dao
interface NoteDao {
    @Insert
    suspend fun addNote(note: Note)


    @Query("SELECT * FROM Note ORDER BY id DESC")
    suspend fun getAllNotes():List<Note>


    @Delete
    suspend fun deleteNote(note:Note)

    @Update
    suspend fun updateNote(note: Note)
}