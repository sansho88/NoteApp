package fr.ft_lyon.tgriffit.noteapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.ft_lyon.tgriffit.noteapp.model.NoteModel

class NoteAdapter(var notes: List<NoteModel>, var listener: NoteListener): RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    interface NoteListener{
        fun onItemClicked(position: Int)
    }

    class NoteViewHolder(view: View): RecyclerView.ViewHolder(view){
        val noteTitletxtV: TextView = view.findViewById(R.id.note_name_txtV);
        val noteDesctxtV: TextView = view.findViewById(R.id.note_desc_txtV);
        val deleteNoteImgBtn: ImageButton = view.findViewById(R.id.delete_note_ImgBtn);
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val context = parent.context
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_note, parent, false)

        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.noteTitletxtV.text = note.title
        holder.noteDesctxtV.text = note.desc
        holder.itemView.setOnClickListener {
            listener.onItemClicked(position)
        }

    }
}