package nl.jovmit.rmapp.ui.artdetails

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation3.ListDetailSceneStrategy
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.ui.NavDisplay
import kotlinx.serialization.Serializable

@Serializable
data class ArtworkDetailsDestination(
  val objectNumber: String
) : NavKey

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
fun EntryProviderScope<Any>.artWorkDetailsScreen() {
  entry<ArtworkDetailsDestination>(
    metadata = NavDisplay.transitionSpec {
      slideInVertically(
        initialOffsetY = { it },
      ) togetherWith ExitTransition.KeepUntilTransitionsFinished
    } + NavDisplay.popTransitionSpec {
      EnterTransition.None togetherWith slideOutVertically(
        targetOffsetY = { it },
      )
    } + NavDisplay.predictivePopTransitionSpec {
      EnterTransition.None togetherWith slideOutVertically(
        targetOffsetY = { it },
      )
    } + ListDetailSceneStrategy.detailPane()
  ) { key ->
    ArtWorkDetailsScreen(objectNumber = key.objectNumber)
  }
}

fun SnapshotStateList<Any>.navigateToArtWorkDetails(objectNumber: String) {
  val destination = ArtworkDetailsDestination(objectNumber)
  add(destination)
}