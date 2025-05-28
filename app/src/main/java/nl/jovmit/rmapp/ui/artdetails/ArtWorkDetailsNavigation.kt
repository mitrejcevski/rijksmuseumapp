package nl.jovmit.rmapp.ui.artdetails

import androidx.navigation3.runtime.EntryProviderBuilder
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import kotlinx.serialization.Serializable

@Serializable
private data class ArtworkDetailsDestination(
    val objectNumber: String
) : NavKey

fun EntryProviderBuilder<NavKey>.artWorkDetailsScreen(
    onNavigateBack: () -> Unit
) {
    entry<ArtworkDetailsDestination> { key ->
        ArtWorkDetailsScreen(
            objectNumber = key.objectNumber,
            onNavigateBack = onNavigateBack
        )
    }
}

fun NavBackStack.navigateToArtWorkDetails(objectNumber: String) {
    val destination = ArtworkDetailsDestination(objectNumber)
    add(destination)
}