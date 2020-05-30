package com.example.notesplus.ui

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.InputMethodManager

import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.findNavController

import com.example.notesplus.R
import com.example.notesplus.data.Note
import com.example.notesplus.data.NoteDatabase
import com.example.notesplus.databinding.FragmentAddNotesFragmentBinding
import kotlinx.android.synthetic.main.fragment_add_notes_fragment.*
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 */
class Add_notes_fragment : BaseFragment() {
  private var note: Note ?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_add_notes_fragment, container, false)
        val binding = DataBindingUtil.inflate<FragmentAddNotesFragmentBinding>(
            layoutInflater,
            R.layout.fragment_add_notes_fragment,
            container,
            false
        )
        arguments?.let {
          note = Add_notes_fragmentArgs.fromBundle(it).note
          binding.editNote.setText(note?.notes)
          binding.editTitle.setText(note?.title)
        }


        binding.buttonSave.setOnClickListener {

            val noteTitle = binding.editTitle.text.toString().trim()
            val noteBody = binding.editNote.text.toString().trim()

            if (noteTitle.isEmpty()){
                binding.editTitle.error = "Title Required"
                binding.editTitle.requestFocus()
                return@setOnClickListener
            }
            if (noteBody.isEmpty()) {
                binding.editNote.error = "Note Required"
                binding.editNote.requestFocus()
                return@setOnClickListener
            }


            launch {

                val mNote = Note(noteTitle, noteBody)
                context?.let {
                    if (note == null) {
                        NoteDatabase(it).getNoteDao().addNote(mNote)
                        Toast.makeText(it, "Note Saved!!", Toast.LENGTH_SHORT).show()
                    } else {
                        mNote.id = note!!.id
                        NoteDatabase(it).getNoteDao().updateNote(mNote)
                        Toast.makeText(it, "Note Updated!!", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            hideKeyboard()
            it.findNavController().navigate(R.id.action_add_notes_fragment_to_home_Fragment)

        }
        return binding.root
    }
    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun deleteNote() {
        AlertDialog.Builder(context).apply {
            setTitle("Are you sure?")
            setMessage("You cannot undo this operation")
            setPositiveButton("Yes") { _, _ ->
                launch {
                    NoteDatabase(context).getNoteDao().deleteNote(note!!)
                    view!!.findNavController().navigate(R.id.action_add_notes_fragment_to_home_Fragment)
                }
            }
            setNegativeButton("No") { _, _ ->

            }
        }.create().show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.delete -> if (note != null) deleteNote() else Toast.makeText(context,"cannot delete an empty note",Toast.LENGTH_SHORT).show()
        }

        when (item.itemId) {
            R.id.share ->  {

                val message: String = edit_note.text.toString()
                val title:String = edit_title.text.toString()
                val intent = Intent()
                intent.action = Intent.ACTION_SEND
                intent.putExtra(Intent.EXTRA_TEXT,"title:${title} note:${message}")
                intent.type = "text/plain"

                startActivity(Intent.createChooser(intent, "Please select app: "))
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }
}

















