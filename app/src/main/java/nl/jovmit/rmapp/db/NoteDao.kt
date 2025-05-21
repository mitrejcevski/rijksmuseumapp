package nl.jovmit.rmapp.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NoteDao {

    @Query("SELECT * FROM Note")
    suspend fun getAllNotes(): List<Note>

    @Insert
    suspend fun insert(vararg note: Note)

    @Delete
    suspend fun delete(note: Note)
}