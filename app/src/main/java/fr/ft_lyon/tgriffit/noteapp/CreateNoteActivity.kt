package fr.ft_lyon.tgriffit.noteapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class CreateNoteActivity : AppCompatActivity() {

    private lateinit var noteTitleEditText: EditText
    private lateinit var noteDescEditText: EditText
    private lateinit var createNoteButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_note)

        noteTitleEditText = findViewById(R.id.note_title_edit)
        noteDescEditText = findViewById(R.id.note_desc_edit)
        createNoteButton = findViewById(R.id.note_confirm_btn)

        createNoteButton.setOnClickListener{
            val title: String = noteTitleEditText.text.toString()
            val desc: String = noteDescEditText.text.toString()
        }
        //todo: intent
    }
}