package com.example.task6.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task6.model.models.Note
import com.example.task6.repository.INoteRepository
import com.example.task6.utils.SingleLiveEvent
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class NoteContentViewModel(private val repository: INoteRepository, private val note: Note?) :
    ViewModel() {

    val title = MutableLiveData<String>()
    val content = MutableLiveData<String>()

    val onSaveNoteSuccessful = SingleLiveEvent<Unit>()
    val onErrorSaveNote = SingleLiveEvent<Unit>()

    init {
        if (note != null) {
            title.value = note.title
            content.value = note.content
        } else {
            title.value = ""
            content.value = ""
        }
    }

    fun saveNote() {
//        var title: String
//        var content: String
//        if (this.title.value == null || this.content.value == null) {
//            title = ""
//            content = ""
//        } else {
//            title = this.title.value.toString()
//            content = this.content.value.toString()
//        }

        val title = this.title.value.toString()
        val content = this.content.value.toString()

        if (title.isEmpty() || content.isEmpty()) {
            onErrorSaveNote.call()
            return
        }

        when (note) {
            null -> {
                val note = Note(getCurrentDate(), title, content)
                viewModelScope.launch { repository.insertNote(note) }
            }
            else -> {
                note.setNoteTitle(title)
                note.setNoteContent(content)
                viewModelScope.launch { repository.updateNote(note) }
            }
        }
        onSaveNoteSuccessful.call()
        return
    }

    private fun getCurrentDate(): String {
        val date = SimpleDateFormat("dd.M.yyyy", Locale.ENGLISH)
        return date.format(Date())
    }
}