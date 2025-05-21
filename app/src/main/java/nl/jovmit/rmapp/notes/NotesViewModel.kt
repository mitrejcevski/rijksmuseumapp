package nl.jovmit.rmapp.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import nl.jovmit.rmapp.db.Note
import nl.jovmit.rmapp.db.NoteDao
import kotlin.random.Random

class NotesViewModel(
    private val notesDao: NoteDao
) : ViewModel() {

    fun insertNote() {
        val number = Random.nextInt(100)
        val title = "Note $number"
        val content = "Note Content $number"
        val note = Note(noteId = number, title = title, content = content, favorite = 0)

        viewModelScope.launch {
            notesDao.insert(note)
        }
    }
}