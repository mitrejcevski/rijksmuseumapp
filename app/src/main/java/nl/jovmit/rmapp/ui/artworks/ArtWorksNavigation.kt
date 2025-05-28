package nl.jovmit.rmapp.ui.artworks

import androidx.navigation3.runtime.EntryProviderBuilder
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import kotlinx.serialization.Serializable

@Serializable
data object ArtWorksDestination : NavKey

fun EntryProviderBuilder<NavKey>.artWorksScreen(
    onNavigateToArtWorkDetails: (objectNumber: String) -> Unit
) {
    entry<ArtWorksDestination> {
        ArtWorksScreen(
            onNavigateToArtWorkDetails = onNavigateToArtWorkDetails
        )
    }
}