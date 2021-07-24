package com.example.task6.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task6.model.models.Note
import com.example.task6.repository.INoteRepository
import kotlinx.coroutines.launch

class TitleNotesViewModel(private val repository: INoteRepository) : ViewModel() {

    fun getAllNotes(): LiveData<List<Note>> {
        return repository.getAllData()
    }

    fun downloadRandomNote() {
        viewModelScope.launch {
            repository.downloadNote((1..100).random())
        }
    }
}