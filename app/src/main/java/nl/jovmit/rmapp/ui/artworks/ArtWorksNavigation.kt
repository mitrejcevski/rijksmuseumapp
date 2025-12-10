package nl.jovmit.rmapp.ui.artworks

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object ArtWorksDestination : NavKey

fun EntryProviderScope<NavKey>.artWorksScreen(
  onNavigateToArtWorkDetails: (objectNumber: String) -> Unit
) {
  entry<ArtWorksDestination> {
    ArtWorksScreen(
      onNavigateToArtWorkDetails = onNavigateToArtWorkDetails
    )
  }
}