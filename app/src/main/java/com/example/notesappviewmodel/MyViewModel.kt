package com.example.notesappviewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyViewModel (application: Application): AndroidViewModel(application){
//    private val noteDao by lazy { NoteDatabase.getDatabase(application).noteDao() }
     private val noteDao:NoteDao
    private val notes: LiveData<List<Note>>

    init {
        noteDao = NoteDatabase.getDatabase(application).noteDao()
        notes = noteDao.getNotes()
    }

    fun getNotes(): LiveData<List<Note>> {
        return notes
    }

    fun addNote(noteText: String){
        CoroutineScope(Dispatchers.IO).launch {
            noteDao.addNote(Note(0, noteText))
        }
    }

    fun editNote(noteID: Int, noteText: String){
        CoroutineScope(Dispatchers.IO).launch {
            noteDao.updateNote(Note(noteID,noteText))
        }
    }

    fun deleteNote(noteID: Int){
        CoroutineScope(Dispatchers.IO).launch {
            noteDao.deleteNote(Note(noteID,""))
        }
    }

}