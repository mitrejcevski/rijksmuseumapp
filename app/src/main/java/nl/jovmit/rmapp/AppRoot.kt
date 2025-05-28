package nl.jovmit.rmapp

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import nl.jovmit.rmapp.ui.artdetails.artWorkDetailsScreen
import nl.jovmit.rmapp.ui.artdetails.navigateToArtWorkDetails
import nl.jovmit.rmapp.ui.artworks.ArtWorksDestination
import nl.jovmit.rmapp.ui.artworks.artWorksScreen

@Composable
fun AppRoot() {
    val backStack = rememberNavBackStack(ArtWorksDestination)
    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            artWorksScreen(
                onNavigateToArtWorkDetails = { objectNumber ->
                    backStack.navigateToArtWorkDetails(objectNumber)
                }
            )
            artWorkDetailsScreen(
                onNavigateBack = { backStack.removeLastOrNull() }
            )
        }
    )
}