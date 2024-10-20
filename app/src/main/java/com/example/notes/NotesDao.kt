package com.example.notes

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface NotesDao {
    @Insert
    suspend fun insert(notes: Notes)
    @Delete
    suspend fun delete(notes: Notes)
    @Update
    suspend fun update(notes: Notes)

    @Query("SELECT * FROM notes_table WHERE notesId = :notesId")
    fun get(notesId: Long): LiveData<Notes>

    @Query("SELECT * FROM notes_table ORDER BY notesId DESC")
    fun getAll(): LiveData<List<Notes>>
}