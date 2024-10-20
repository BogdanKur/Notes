package com.example.notes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("notes_table")
data class Notes(
    @PrimaryKey(true)
    var notesId: Long = 0L,
    @ColumnInfo("notes_name")
    var notesName: String = ""
)
