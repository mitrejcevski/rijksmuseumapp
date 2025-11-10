package nl.jovmit.rmapp.ui.artworks

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation3.ListDetailSceneStrategy
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object ArtWorksDestination : NavKey

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
fun EntryProviderScope<Any>.artWorksScreen(
    onNavigateToArtWorkDetails: (objectNumber: String) -> Unit
) {
    entry<ArtWorksDestination>(
      metadata = ListDetailSceneStrategy.listPane(
        detailPlaceholder = {
          Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "Heey")
          }
        }
      )
    ) {
        ArtWorksScreen(
            onNavigateToArtWorkDetails = onNavigateToArtWorkDetails
        )
    }
}