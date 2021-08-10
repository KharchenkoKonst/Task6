package com.example.task6.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.task6.model.database.AppDatabase
import com.example.task6.model.models.Note
import com.example.task6.network.ApiRequests
import com.example.task6.network.NetworkModel
import com.example.task6.utils.BASE_URL
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

open class RepositoryImpl private constructor(context: Context) : INoteRepository {
    private val dao = AppDatabase.getDatabase(context).noteDao()
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiRequests::class.java)

    override suspend fun downloadNote(id: Int) {
        val note = api.getData(id)
        dao.insert(Note("date", note.title, note.body))
    }

    override suspend fun insertNote(note: Note) {
        dao.insert(note)
    }

    override suspend fun updateNote(note: Note) {
        dao.update(note)
    }

    override fun getAllData(): LiveData<List<Note>> = dao.getAll()

    companion object {
        private var INSTANCE: RepositoryImpl? = null
        fun getRepository(context: Context): RepositoryImpl {
            if (INSTANCE != null) {
                return INSTANCE as RepositoryImpl
            } else {
                INSTANCE = RepositoryImpl(context)
                return INSTANCE as RepositoryImpl
            }
        }
    }
}