package com.example.notesappviewmodel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "NotesTable")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val pk: Int,
    val noteText: String)