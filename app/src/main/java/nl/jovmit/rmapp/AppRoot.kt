package nl.jovmit.rmapp

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation3.rememberListDetailSceneStrategy
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import nl.jovmit.rmapp.ui.artdetails.artWorkDetailsScreen
import nl.jovmit.rmapp.ui.artdetails.navigateToArtWorkDetails
import nl.jovmit.rmapp.ui.artworks.ArtWorksDestination
import nl.jovmit.rmapp.ui.artworks.artWorksScreen

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun AppRoot() {
  val backStack = remember { mutableStateListOf<Any>(ArtWorksDestination) }
  val strategy = rememberListDetailSceneStrategy<Any>()

  NavDisplay(
    backStack = backStack,
    onBack = { backStack.removeLastOrNull() },
    sceneStrategy = strategy,
    entryProvider = entryProvider {
      artWorksScreen(
        onNavigateToArtWorkDetails = { objectNumber ->
          backStack.navigateToArtWorkDetails(objectNumber)
        }
      )
      artWorkDetailsScreen(
        onNavigateBack = { backStack.removeLastOrNull() }
      )
    },
    transitionSpec = {
      slideInHorizontally(initialOffsetX = { it }) togetherWith
        slideOutHorizontally(targetOffsetX = { -it })
    },
    popTransitionSpec = {
      slideInHorizontally(initialOffsetX = { -it }) togetherWith
        slideOutHorizontally(targetOffsetX = { it })
    },
  )
}