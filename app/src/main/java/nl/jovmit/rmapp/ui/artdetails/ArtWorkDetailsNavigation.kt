package nl.jovmit.rmapp.ui.artdetails

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
private data class ArtworkDetailsDestination(
  val objectNumber: String
)

fun NavGraphBuilder.artWorkDetailsScreen(
  onNavigateBack: () -> Unit
) {
  composable<ArtworkDetailsDestination> {
    ArtWorkDetailsScreen(
      onNavigateBack = onNavigateBack
    )
  }
}

fun NavController.navigateToArtWorkDetails(objectNumber: String) {
  val destination = ArtworkDetailsDestination(objectNumber)
  navigate(destination)
}