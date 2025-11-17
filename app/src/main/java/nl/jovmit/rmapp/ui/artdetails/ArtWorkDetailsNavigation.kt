package nl.jovmit.rmapp.ui.artdetails

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data class ArtworkDetailsDestination(
  val objectNumber: String
) : NavKey

fun EntryProviderScope<NavKey>.artWorkDetailsScreen(
  onNavigateBack: () -> Unit
) {
  entry<ArtworkDetailsDestination> {
    ArtWorkDetailsScreen(
      onNavigateBack = onNavigateBack
    )
  }
}

fun NavBackStack<NavKey>.navigateToArtWorkDetails(objectNumber: String) {
  val destination = ArtworkDetailsDestination(objectNumber)
  add(destination)
}