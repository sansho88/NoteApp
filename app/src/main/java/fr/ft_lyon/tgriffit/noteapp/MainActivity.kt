package fr.ft_lyon.tgriffit.noteapp

import android.app.Activity
import android.content.Intent
import android.graphics.Color.rgb
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView

import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import fr.ft_lyon.tgriffit.noteapp.model.NoteModel
import fr.ft_lyon.tgriffit.noteapp.ui.theme.NoteAppTheme
import fr.ft_lyon.tgriffit.noteapp.viewmodel.NotesListViewModel


class MainActivity : AppCompatActivity(), NoteAdapter.NoteListener {

    private lateinit var    addNewNoteButton : FloatingActionButton;
    private lateinit var    notesAvailable : TextView
    private lateinit var    notesListViewModel: NotesListViewModel
    private lateinit var    noteRecyclerView: RecyclerView
    private var             notes = mutableListOf<NoteModel>()

    private var activityResult = registerForActivityResult(ActivityResultContracts
        .StartActivityForResult()) {
            result: ActivityResult ->
                if (result.resultCode == Activity.RESULT_OK){
                    val data = result.data
                    val noteTitle: String = data?.getStringExtra(NOTE_TITLE) ?: ""
                    val noteDesc: String = data?.getStringExtra(NOTE_DESC) ?: ""

                    val newNote = NoteModel(noteTitle, noteDesc)
                    notesListViewModel.addItem(0, newNote)
                    noteRecyclerView.adapter?.notifyItemChanged(0)
                    //updateNbNotes()
                }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        addNewNoteButton = findViewById(R.id.createNoteButton)
        notesAvailable = findViewById(R.id.notes_available)
        notesListViewModel = ViewModelProvider(this).get(NotesListViewModel::class.java)
        if (this::notesListViewModel.isInitialized) {
            initNoteRecyclerView()
            notesListViewModel.noteList.observe(this) { _ -> updateNbNotes() }
        }
        updateNbNotes() //init text

        addNewNoteButton.setOnClickListener{
           val intent = Intent(this, CreateNoteActivity::class.java)
            activityResult.launch(intent)
        }

    }

    private fun updateNbNotes() = notesAvailable.setText("${notesListViewModel.size} note${putS(notesListViewModel.size ?: 0)} available")
    private fun initNoteRecyclerView() {
        noteRecyclerView = findViewById(R.id.note_recyclerView)
        val adapter: NoteAdapter = NoteAdapter( notesListViewModel.noteList.value?.toList()!!, this)
        val layoutManager = LinearLayoutManager(this)

        noteRecyclerView.adapter = adapter
        noteRecyclerView.layoutManager = layoutManager
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
        Log.d("MainActivity", "onItemClicked: ${notes[position]}")
    }

    override fun onDeleteNoteClicked(position: Int) {
       showDeleteNoteAlertDialog(notesListViewModel[position]!!, position)
    }

   /* override fun onContentChanged() {
        super.onContentChanged()
        if(this::notesAvailable.isInitialized)
            updateNbNotes()
    }*/

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus && this::notesAvailable.isInitialized)
            updateNbNotes()
    }

    override fun getCurrentFocus(): View? {
        return super.getCurrentFocus()
    }

    override fun hasWindowFocus(): Boolean {
        return super.hasWindowFocus()
    }

    private fun deleteNote(position: Int){
        notesListViewModel.removeItem(position)
        noteRecyclerView.adapter?.notifyItemRemoved(position)
        //updateNbNotes()
    }
}

/***
 * Put an -s at the end of the word. Useful for plurals
 */
fun putS(nb:Int) = if (nb > 1) 's' else ""

