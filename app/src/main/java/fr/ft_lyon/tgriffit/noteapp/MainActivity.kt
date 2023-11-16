package fr.ft_lyon.tgriffit.noteapp

import android.app.Activity
import android.content.Intent
import android.graphics.Color.rgb
import android.os.Bundle
import android.util.Log
import android.widget.TextView

import androidx.activity.compose.setContent
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
    var notes = mutableListOf<NoteModel>(
        NoteModel("My first Note", "J'aime beaucoup en vrai!"),
        NoteModel("My 2nd Note", "C'est toujours aussi cool!")
    )


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
            var noteTitle: String = ""
            var noteDesc: String = ""
            intent.putExtra("noteTitle", noteTitle)
            intent.putExtra("noteDesc", noteDesc)
            startActivity(intent)


            notes.add(NoteModel("Note ${notes.size + 1}", "Description of Note ${notes.size + 1}"))
            updateNoteList()

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

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
            text = "Bonjour $name!",
            modifier = modifier
    )

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NoteAppTheme {
        Greeting("Sanshoid")
    }
}

/***
 * Put an -s at the end of the word. Useful for plurals
 */
fun putS(nb:Int) = if (nb > 1) 's' else ""

