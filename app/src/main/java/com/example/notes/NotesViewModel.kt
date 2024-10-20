package com.example.notes

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class NotesViewModel(val dao:NotesDao): ViewModel() {
    var newNotes = ""
    val notes = dao.getAll()

    fun addNotes() {
        viewModelScope.launch {
            val note = Notes()
            note.notesName = newNotes
            dao.insert(note)
        }
    }
}