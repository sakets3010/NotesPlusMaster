package com.example.notesplus.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager

import com.example.notesplus.R
import com.example.notesplus.data.NoteDatabase
import com.example.notesplus.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 */

class Home_Fragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         //=StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL)
        val binding = DataBindingUtil.inflate<FragmentHomeBinding>(inflater, R.layout.fragment_home, container, false)
        launch {
            context?.let {
                val notes = NoteDatabase(it).getNoteDao().getAllNotes()
                recyclerView!!.apply {
                    layoutManager=StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL)
                    adapter=notesAdapter(notes)
                }
            }
        }

        binding.addNotes.setOnClickListener{
            it.findNavController().navigate(R.id.action_home_Fragment_to_add_notes_fragment)
        }

        return binding.root
    }

}
