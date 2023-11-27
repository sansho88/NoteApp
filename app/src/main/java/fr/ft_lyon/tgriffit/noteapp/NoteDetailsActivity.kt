package fr.ft_lyon.tgriffit.noteapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import android.window.OnBackInvokedDispatcher

class NoteDetailsActivity : AppCompatActivity() {
    lateinit var noteTitleEditText: EditText
    lateinit var noteDescEditText: EditText
    lateinit var updateNoteButton: Button
    lateinit var noteTitle: String
    lateinit var noteDesc: String
    var position: Int = -1
    var isUpdated = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_details)
        initViews()
        fetchData()

        updateNoteButton.setOnClickListener {
            isUpdated = true
            noteTitle = noteTitleEditText.text.toString()
            noteDesc  = noteDescEditText.text.toString()
            finish()
        }
    }

    private fun initViews() {
        noteTitleEditText = findViewById(R.id.note_title_edit)
        noteDescEditText = findViewById(R.id.note_desc_edit)
        updateNoteButton = findViewById(R.id.note_update_btn)
    }

    override fun onBackPressed() {
        if (isUpdated)
        {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(NOTE_TITLE, noteTitle)
            intent.putExtra(NOTE_DESC, noteDesc)
            intent.putExtra(NOTE_POSITION, position)
            setResult(Activity.RESULT_OK, intent)
        }
        super.onBackPressed()
    }
    private fun fetchData(){
        if (intent.hasExtra(NOTE_TITLE)){
            noteTitle = intent.getStringExtra(NOTE_TITLE)!!
            noteDesc = intent.getStringExtra(NOTE_DESC)!!
            position = intent.getIntExtra(NOTE_POSITION, -1)

            noteTitleEditText.setText(noteTitle)
            noteDescEditText.setText(noteDesc)
        }
    }
}