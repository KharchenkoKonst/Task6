package com.example.task6.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.task6.BR
import com.example.task6.R
import com.example.task6.databinding.RecyclerItemBinding
import com.example.task6.model.models.Note

class NoteRecyclerAdapter(private val context: Context, private val onSelect:(Note) -> Unit) :
    RecyclerView.Adapter<NoteRecyclerAdapter.ViewHolder>() {

    private var dataNotes = emptyList<Note>()

    fun setData(newData: List<Note>) {
        dataNotes = newData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: RecyclerItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.recycler_item,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataNotes[position], onSelect)
    }

    override fun getItemCount() = dataNotes.size


    class ViewHolder(private val binding: RecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note, onSelect: (Note) -> Unit) {
            binding.setVariable(BR.note, note)
            binding.executePendingBindings()

            binding.root.setOnClickListener {
                onSelect(note)
            }
        }
    }

}