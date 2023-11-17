package fr.ft_lyon.tgriffit.noteapp

import android.app.Activity
import android.content.Intent
import android.graphics.Color.rgb
import android.os.Bundle
import android.util.Log
import android.widget.TextView

import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import fr.ft_lyon.tgriffit.noteapp.model.NoteModel
import fr.ft_lyon.tgriffit.noteapp.ui.theme.NoteAppTheme


class MainActivity : AppCompatActivity(), NoteAdapter.NoteListener {

    lateinit var addNewNoteButton : FloatingActionButton;
    lateinit var notesAvailable : TextView

    private var activityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK){
                val data = result.data
                val noteTitle: String = data?.getStringExtra(NOTE_TITLE) ?: ""
                val noteDesc: String = data?.getStringExtra(NOTE_DESC) ?: ""

                val newNote = NoteModel(noteTitle, noteDesc)
                notes.add(0, newNote)
                noteRecyclerView.adapter?.notifyItemChanged(0)
                updateNbNotes()
            }
    }

    var notes = mutableListOf<NoteModel>()


    lateinit var noteRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initNoteRecyclerView()
        addNewNoteButton = findViewById(R.id.createNoteButton)
        notesAvailable = findViewById(R.id.notes_available)
        updateNbNotes()

        addNewNoteButton.setOnClickListener{
           val intent = Intent(this, CreateNoteActivity::class.java)
            activityResult.launch(intent)
        }

    }

    private fun updateNbNotes() = notesAvailable.setText("${notes.size} note${putS(notes.size)} available")

    private fun updateNoteList(){
        noteRecyclerView.adapter = NoteAdapter(notes, this)
        updateNbNotes()
    }

    private fun initNoteRecyclerView() {
        noteRecyclerView = findViewById(R.id.note_recyclerView)
        val adapter: NoteAdapter = NoteAdapter(notes, this)
        val layoutManager = LinearLayoutManager(this)

        noteRecyclerView.adapter = adapter
        noteRecyclerView.layoutManager = layoutManager
    }

    override fun onItemClicked(position: Int) {
        Log.d("MainActivity", "onItemClicked: ${notes[position]}")
    }
}

/***
 * Put an -s at the end of the word. Useful for plurals
 */
fun putS(nb:Int) = if (nb > 1) 's' else ""

