package fr.ft_lyon.tgriffit.noteapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.ft_lyon.tgriffit.noteapp.model.NoteModel
import java.text.FieldPosition

class NotesListViewModel: ViewModel() {
    private var internalList = MutableLiveData<ArrayList<NoteModel>>()
    val noteList: LiveData<ArrayList<NoteModel>> get() = internalList
    fun size() = noteList.value?.size ?: 0
    operator fun get(index: Int): NoteModel?{
        return internalList.value?.getOrNull(index)
    }
    init {
        internalList.value = ArrayList()
    }

    fun addItem(note: NoteModel){
        val list = internalList.value ?: ArrayList()
        list.add(note)
        internalList.value = list
    }
    fun addItem(position: Int, note: NoteModel){
        val list = internalList.value ?: ArrayList()
        list.add(position, note)
        internalList.value = list
    }
    fun removeItem(position: Int){
        val list = internalList.value ?: ArrayList()
        list.removeAt(position)
        internalList.value = list
    }
    fun removeItem(note: NoteModel){
        val list = internalList.value ?: ArrayList()
        list.remove(note)
        internalList.value = list
    }

}