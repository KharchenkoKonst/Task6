package com.example.task6.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.task6.R
import com.example.task6.databinding.FragmentNoteContentBinding
import com.example.task6.repository.RepositoryImpl
import com.example.task6.utils.NOTE_DATA
import com.example.task6.viewmodel.NoteContentViewModel
import com.example.task6.viewmodel.NoteContentViewModelFactory

class NoteContentFragment : Fragment() {

    private val contentViewModelFactory: NoteContentViewModelFactory by lazy {
        NoteContentViewModelFactory(
            RepositoryImpl.getRepository(requireContext()), arguments?.getParcelable(NOTE_DATA)
        )
    }
    private val viewModel: NoteContentViewModel by viewModels {
        contentViewModelFactory
    }
    private lateinit var binding: FragmentNoteContentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_note_content, container, false)
        subscribeToVM()
        return binding.root
    }

    private fun subscribeToVM() {
        binding.viewModel = viewModel
        binding.saveNoteBtn.setOnClickListener {
            viewModel.saveNote()
        }
        viewModel.onErrorSaveNote.observe(viewLifecycleOwner, {
            Toast.makeText(
                requireContext(),
                getString(R.string.empty_note_toast),
                Toast.LENGTH_LONG
            ).show()
        })
        viewModel.onSaveNoteSuccessful.observe(viewLifecycleOwner, {
            Toast.makeText(
                requireContext(),
                getString(R.string.save_note_toast),
                Toast.LENGTH_LONG
            ).show()
        })
    }
}
