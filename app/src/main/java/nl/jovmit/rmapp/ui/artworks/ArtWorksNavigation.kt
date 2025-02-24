package nl.jovmit.rmapp.ui.artworks

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object ArtWorksDestination

fun NavGraphBuilder.artWorksScreen(
  onNavigateToArtWorkDetails: (objectNumber: String) -> Unit
) {
  composable<ArtWorksDestination> {
    ArtWorksScreen(
      onNavigateToArtWorkDetails = onNavigateToArtWorkDetails
    )
  }
}