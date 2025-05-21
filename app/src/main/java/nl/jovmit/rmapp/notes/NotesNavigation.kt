package nl.jovmit.rmapp.notes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object NotesDestination

fun NavGraphBuilder.notesScreen() {
    composable<NotesDestination> {
        NotesScreen()
    }
}