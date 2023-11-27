package fr.ft_lyon.tgriffit.noteapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import fr.ft_lyon.tgriffit.noteapp.model.NoteModel
import fr.ft_lyon.tgriffit.noteapp.ui.theme.NoteAppTheme
import fr.ft_lyon.tgriffit.noteapp.viewmodel.NotesListViewModel


class MainActivity : AppCompatActivity(), NoteAdapter.NoteListener {

    private lateinit var    addNewNoteButton : FloatingActionButton
    private lateinit var    notesAvailable : TextView
    private lateinit var    notesListViewModel: NotesListViewModel
    private lateinit var    noteRecyclerView: RecyclerView

    private var activityResult = registerForActivityResult(ActivityResultContracts
        .StartActivityForResult()) {
            result: ActivityResult ->
                if (result.resultCode == Activity.RESULT_OK){
                    val data = result.data
                    val noteTitle: String = data?.getStringExtra(NOTE_TITLE) ?: ""
                    val noteDesc: String = data?.getStringExtra(NOTE_DESC) ?: ""

                    val newNote = NoteModel(noteTitle, noteDesc)
                    notesListViewModel.addItem(0, newNote)
                    noteRecyclerView.adapter?.notifyItemInserted(0)
                    Log.d(TAG, "ActivityResult: ${newNote.title} created. NoteList size = ${notesListViewModel.size()})")
                    //updateNbNotes()
                }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addNewNoteButton = findViewById(R.id.createNoteButton)
        notesAvailable = findViewById(R.id.notes_available)
        notesListViewModel = ViewModelProvider(this)[NotesListViewModel::class.java]
        updateNbNotes() //init text

        if (this::notesListViewModel.isInitialized) {
            initNoteRecyclerView()
        }

        addNewNoteButton.setOnClickListener{
           val intent = Intent(this, CreateNoteActivity::class.java)
            activityResult.launch(intent)
        }

    }

    private fun updateNbNotes() {
        notesAvailable.text = "${notesListViewModel.size()} note${putS(notesListViewModel.size())} available"
    }
    private fun initNoteRecyclerView() {
        noteRecyclerView = findViewById(R.id.note_recyclerView)
        val layoutManager = LinearLayoutManager(this)
        noteRecyclerView.layoutManager = layoutManager

        notesListViewModel.noteList.observe(this){ newList ->
            val adapter = NoteAdapter(newList, this)
            noteRecyclerView.adapter = adapter
            updateNbNotes()
        }
    }

    private fun showDeleteNoteAlertDialog(note: NoteModel, position: Int){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete ${note.title} ?")
            .setMessage("Are you sure to delete this ?")
            .setIcon(android.R.drawable.ic_menu_delete)
            .setPositiveButton("Delete"){ dialog, _ ->
                dialog.dismiss()
                deleteNote(position)
            }
            .setNegativeButton("Cancel"){dialog, _ ->
                dialog.dismiss()
            }
        val alertDialog = builder.create()
        alertDialog.show()
    }
    override fun onItemClicked(position: Int) {
        Log.d("MainActivity", "onItemClicked: ${notesListViewModel[position]}")
    }

    override fun onDeleteNoteClicked(position: Int) {
       showDeleteNoteAlertDialog(notesListViewModel[position]!!, position)
    }

    private fun deleteNote(position: Int){
        notesListViewModel.removeItem(position)
        noteRecyclerView.adapter?.notifyItemRemoved(position)
    }
}

/***
 * Put an -s at the end of the word. Useful for plurals
 */
fun putS(nb:Int) = if (nb > 1) 's' else ""

