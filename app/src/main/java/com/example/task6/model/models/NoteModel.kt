package com.example.task6.model.models

import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Note(
    val date: String,
    var title: String,
    var content: String
) : Parcelable, BaseObservable() {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    @Bindable
    fun getNoteTitle() = title
    @Bindable
    fun setNoteTitle(newTitle: String){
        this.title = newTitle
        notifyPropertyChanged(com.example.task6.BR.noteTitle)
    }

    @Bindable
    fun getNoteContent() = content
    @Bindable
    fun setNoteContent(newContent: String){
        this.content = newContent
        notifyPropertyChanged(com.example.task6.BR.noteContent)
    }

}
