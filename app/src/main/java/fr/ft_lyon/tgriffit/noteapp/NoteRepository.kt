package fr.ft_lyon.tgriffit.noteapp

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import fr.ft_lyon.tgriffit.noteapp.model.NoteModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

/*
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class NoteRepository(private val dataStore: DataStore<List<NoteModel>>) {
    val gson = Gson()
    private val noteKey = stringPreferencesKey("savedNotes")

    fun getSavedNotes(): Flow<List<NoteModel>> = flow {
        dataStore.data.map { preferences ->
            val serializedNotes = preferences[noteKey]
            gson.fromJson(serializedNotes , object: TypeToken<List<NoteModel>>() {}.type)
        }
    }
}*/

class NoteRepository(private val sharedPreferences: SharedPreferences) {
    val gson = Gson()

    fun getSavedNotes(): Flow<List<NoteModel>> = flow {
        val serializedNotes = sharedPreferences.getString(NOTES_SAVED, null)
        val typeToken = object: TypeToken<List<NoteModel>>(){}.type
        if (serializedNotes != null)
            emit(gson.fromJson(serializedNotes, typeToken))
        else
            emit(ArrayList<NoteModel>())
    }

    fun saveNotes(notesToSave: List<NoteModel>){
        val serializedNoteSaved = gson.toJson(notesToSave)
        with(sharedPreferences.edit()){
            putString(NOTES_SAVED, serializedNoteSaved)
            apply()
        }
    }
}