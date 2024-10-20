package com.example.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView

class NotesAdapter(val listNotes: List<Notes>, val navController: NavController, val listener: onClickInterface): RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {
    class NotesViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val nameTask = view.findViewById<Button>(R.id.tvNameTask)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.notes, parent, false)
        return NotesViewHolder(layoutInflater)
    }

    override fun getItemCount(): Int = listNotes.size

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.nameTask.text = listNotes[position].notesName
        holder.nameTask.setOnClickListener {
            listener.onClick(listNotes[position])
        }
    }

}