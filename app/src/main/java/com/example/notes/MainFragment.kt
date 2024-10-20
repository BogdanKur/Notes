package com.example.notes

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.notes.databinding.FragmentMainBinding

class MainFragment : Fragment(), onClickInterface {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding.root
        val navController = findNavController()
        binding.toolbar.setupWithNavController(navController)
        navController.addOnDestinationChangedListener{_, destination, _ ->
            destination.label = "Заметки"
            binding.toolbar.title = destination.label
        }
        val application = requireNotNull(this.activity).application
        val dao = NotesDatabase.getInstance(application).notesDao
        val viewModelFactory = NotesViewModelFactory(dao)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(NotesViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.notes.observe(viewLifecycleOwner){ list->
            val adapter = NotesAdapter(list, navController, this)
            binding.rvListTasks.adapter = adapter
        }

        binding.imgBtnExit.setOnClickListener {
            val resource = activity?.getDrawable(R.color.grayTextColor)
            binding.toolbar.title = "Заметки"
            binding.toolbar.setBackgroundDrawable(resource)
            binding.imgBtnEdit.visibility = View.GONE
            binding.imgBtnExit.visibility = View.GONE
        }

        scheduleNotification(requireContext())

        return view
    }

    override fun onClick(note: Notes) {
        val resource = activity?.getDrawable(R.color.black)
        binding.toolbar.title = "Выбрано"
        binding.toolbar.setBackgroundDrawable(resource)
        binding.imgBtnEdit.visibility = View.VISIBLE
        binding.imgBtnExit.visibility = View.VISIBLE
        binding.imgBtnEdit.setOnClickListener {
            val bundle = Bundle().apply {
                putLong("NOTEID", note.notesId)
            }
            findNavController().navigate(R.id.action_mainFragment_to_editFragment, bundle)
        }
    }

    fun scheduleNotification(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, NotificationReceiver::class.java)

        // Используем FLAG_IMMUTABLE
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val intervalMillis = 1000  // Интервал в 1 час
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis() + intervalMillis,
            intervalMillis.toLong(),
            pendingIntent
        )
    }



}