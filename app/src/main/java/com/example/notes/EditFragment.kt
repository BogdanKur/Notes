package com.example.notes

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.notes.databinding.FragmentEditBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch


class EditFragment : Fragment() {
    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!
    private var noteId: Long = -1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditBinding.inflate(inflater, container, false)
        val view = binding.root
        val navController = findNavController()
        binding.toolbar.setupWithNavController(navController)
        navController.addOnDestinationChangedListener{_,destination,_ ->
            destination.label = "Редактирование"
            binding.toolbar.title = destination.label
        }
        val application = requireNotNull(this.activity).application
        val dao = NotesDatabase.getInstance(application).notesDao
        arguments.let { bundle->
            noteId = bundle?.getLong("NOTEID")!!
        }

        val viewModelFactory = EditViewModelFactory(noteId, dao)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(EditViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.btnEdit.setOnClickListener {
            viewModel.updateTask()
            navController.navigate(R.id.action_editFragment_mainFragment)
        }

        return view
    }

}