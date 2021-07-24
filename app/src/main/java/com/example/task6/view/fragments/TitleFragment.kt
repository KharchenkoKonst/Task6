package com.example.task6.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.task6.R
import com.example.task6.databinding.FragmentTitleBinding
import com.example.task6.model.models.Note
import com.example.task6.repository.RepositoryImpl
import com.example.task6.utils.NOTE_DATA
import com.example.task6.view.activities.MainActivity
import com.example.task6.view.adapters.NoteRecyclerAdapter
import com.example.task6.viewmodel.TitleNotesViewModel
import com.example.task6.viewmodel.TitleNotesViewModelFactory

class TitleFragment : Fragment() {

    private val viewModelFactory: TitleNotesViewModelFactory by lazy {
        TitleNotesViewModelFactory(
            RepositoryImpl.getRepository(requireContext())
        )
    }
    private val viewModel: TitleNotesViewModel by viewModels { viewModelFactory }
    private lateinit var binding: FragmentTitleBinding
    private lateinit var adapter: NoteRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_title, container, false)
        (requireActivity() as MainActivity).setSupportActionBar(binding.toolbar)

        bindInit()
        subscribeToVM()

        return binding.root
    }

    private fun bindInit() {
        binding.lifecycleOwner = this
        binding.newNoteBtn.setOnClickListener {
            (requireActivity() as MainActivity).navController.navigate(R.id.action_titleFragment_to_noteContentFragment)
        }

        //как сделать лучше?
        adapter = NoteRecyclerAdapter(requireContext()) { note ->
            openNote(note)
        }
        binding.recycler.adapter = adapter
    }

    private fun subscribeToVM() {
        viewModel.getAllNotes().observe(viewLifecycleOwner, { notes ->
            adapter.setData(notes)
        })
        binding.downloadBtn.setOnClickListener {
            viewModel.downloadRandomNote()
        }

    }

    private fun openNote(note: Note) {
        val bundle = Bundle().apply { putParcelable(NOTE_DATA, note) }
        (requireActivity() as MainActivity).navController.navigate(
            R.id.action_titleFragment_to_noteContentFragment,
            bundle
        )
    }


}