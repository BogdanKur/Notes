package com.example.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class EditViewModel(val dao: NotesDao, val notesId: Long): ViewModel() {
    var editNotes = ""
    fun updateTask() {
        val note = Notes(notesId, editNotes)
        viewModelScope.launch {
            dao.update(note)
        }
    }
}