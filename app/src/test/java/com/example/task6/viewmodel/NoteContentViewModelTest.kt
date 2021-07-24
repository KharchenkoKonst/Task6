package com.example.task6.viewmodel

import com.example.task6.model.models.Note
import com.example.task6.repository.INoteRepository
import com.example.task6.repository.RepositoryImpl
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import junit.framework.Assert.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.mockito.Mockito.mock

@RunWith(JUnit4::class)
class NoteContentViewModelTest {

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    private lateinit var viewModel: NoteContentViewModel
    private lateinit var repository: INoteRepository
    private var note: Note? = null

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        repository = mock(RepositoryImpl::class.java)
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @Test
    fun testSaveNoteWithNonData() {
        viewModel = NoteContentViewModel(repository, null)
        viewModel.title.value = ""
        viewModel.content.value = ""

        viewModel.saveNote()

        var successSaved = false
        viewModel.onSaveNoteSuccessful.observeForever {
            successSaved = true
        }
        assertFalse(successSaved)

        var errorSaved = false
        viewModel.onErrorSaveNote.observeForever {
            errorSaved = true
        }
        assertTrue(errorSaved)

    }

    @Test
    fun testSaveNoteWithNullArg() {
        viewModel = NoteContentViewModel(repository, null)

        viewModel.title.value = "title"
        viewModel.content.value = "content"

        runBlocking {
            viewModel.saveNote()
        }

        var errorSaved = false
        viewModel.onErrorSaveNote.observeForever {
            errorSaved = true
        }
        assertFalse(errorSaved)

        var successSaved = false
        viewModel.onSaveNoteSuccessful.observeForever {
            successSaved = true
        }
        assertTrue(successSaved)
    }

    @Test
    fun testSaveNoteWithArgs() {
        viewModel = NoteContentViewModel(repository, Note("date", "title", "content"))
        note?.setNoteTitle("new title")
        note?.setNoteContent("new content")

        runBlocking {
            viewModel.saveNote()
        }

        var errorSaved = false
        viewModel.onErrorSaveNote.observeForever {
            errorSaved = true
        }
        assertFalse(errorSaved)

        var successSaved = false
        viewModel.onSaveNoteSuccessful.observeForever {
            successSaved = true
        }
        assertTrue(successSaved)
    }
}