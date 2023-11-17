package fr.ft_lyon.tgriffit.noteapp

import android.app.Activity
import android.content.Intent
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
            val intent = Intent(this, MainActivity::class.java)
            val noteTitle: String = noteTitleEditText.text.toString()
            val noteDesc: String = noteDescEditText.text.toString()

            intent.putExtra(NOTE_TITLE, noteTitle)
            intent.putExtra(NOTE_DESC, noteDesc)
            setResult(Activity.RESULT_OK, intent)
            if (noteTitle.isNotEmpty())
                finish()
        }

    }
}