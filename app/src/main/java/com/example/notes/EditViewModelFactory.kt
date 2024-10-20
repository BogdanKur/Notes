package com.example.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class EditViewModelFactory(private val noteId: Long, private val dao: NotesDao):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(EditViewModel::class.java)) {
            return EditViewModel(dao, noteId) as T
        }
        throw IllegalArgumentException("unknown viewModel")
    }
}