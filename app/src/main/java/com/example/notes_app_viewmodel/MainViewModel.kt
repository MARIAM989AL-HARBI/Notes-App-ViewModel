package com.example.notes_app_viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
//ViewModel class allows data to survive configuration changes such as screen rotations.
import kotlinx.coroutines.launch
class MainViewModel(application: Application): AndroidViewModel(application) {
    private val repository: NoteRepository
    private val notes: LiveData<List<Note>>

    init {
        val noteDao = NoteDatabase.getDatabase(application).noteDao()
        repository = NoteRepository(noteDao)
        notes = repository.getNotes
    }

    fun getNotes(): LiveData<List<Note>>{
        return notes
    }

    fun addNote(noteText: String){
        CoroutineScope(Dispatchers.IO).launch {
            repository.addNote(Note(0, noteText))
        }
    }

    fun editNote(noteID: Int, noteText: String){
        CoroutineScope(Dispatchers.IO).launch {
            repository.updateNote(Note(noteID,noteText))
        }
    }

    fun deleteNote(noteID: Int){
        CoroutineScope(Dispatchers.IO).launch {
            repository.deleteNote(Note(noteID,""))
        }
    }
}