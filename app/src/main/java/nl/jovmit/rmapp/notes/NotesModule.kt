package nl.jovmit.rmapp.notes

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val notesModule = module {

    viewModel<NotesViewModel> {
        NotesViewModel(
            notesDao = get()
        )
    }
}