package com.example.notesplus.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.notesplus.R
import com.example.notesplus.data.Note
import kotlinx.android.synthetic.main.item_list.view.*

class notesAdapter(private val notes:List<Note>) : RecyclerView.Adapter<notesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder:ViewHolder, position: Int) {
        val note = notes[position]
        holder.Title.text=note.title
        holder.Description.text=note.notes
        holder.itemView.setOnClickListener{
            val action = Home_FragmentDirections.actionHomeFragmentToAddNotesFragment()
            action.note=note
            it.findNavController().navigate(action)
        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val Title: TextView =itemView.text_view_title
        val Description: TextView =itemView.text_view_note
    }

}