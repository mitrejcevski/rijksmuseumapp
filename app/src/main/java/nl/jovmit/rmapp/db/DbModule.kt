package nl.jovmit.rmapp.db

import androidx.room.Room
import org.koin.dsl.module

val dbModule = module {

    single<NotesDatabase> {
        Room.databaseBuilder(get(), NotesDatabase::class.java, "notes.db")
            .addMigrations(MIGRATION_1_2)
            .build()
    }

    single<NoteDao> {
        get<NotesDatabase>().noteDao()
    }
}