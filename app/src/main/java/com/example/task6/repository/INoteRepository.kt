package com.example.task6.repository

import androidx.lifecycle.LiveData
import com.example.task6.model.models.Note


interface INoteRepository {
    suspend fun downloadNote(id: Int)
    suspend fun insertNote(note: Note)
    suspend fun updateNote(note: Note)
    fun getAllData(): LiveData<List<Note>>
}
