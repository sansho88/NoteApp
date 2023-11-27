package fr.ft_lyon.tgriffit.noteapp.model

import android.icu.text.CaseMap.Title

class NoteModel(private var title: String, private var desc: String) {

    fun setTitle(newTitle: String){
        this.title = newTitle
    }

    fun setDesc(value: String) {
        desc = value
    }

    fun getTitle() = title
    fun getDesc() = desc

    fun update(newTitle : String = title, newDesc: String = desc){
        title = newTitle
        desc = newDesc
    }

    fun update(note: NoteModel) = update(note.title, note.desc)

    override fun toString(): String {
        return """
            title: $title,
            description: $desc
            """.trimIndent()
    }
}