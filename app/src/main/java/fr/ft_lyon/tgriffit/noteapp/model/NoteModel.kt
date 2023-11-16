package fr.ft_lyon.tgriffit.noteapp.model

import android.icu.text.CaseMap.Title

class NoteModel(var title: String, var desc: String) {

    override fun toString(): String {
        return """
            title: $title,
            description: $desc
            """.trimIndent()
    }
}