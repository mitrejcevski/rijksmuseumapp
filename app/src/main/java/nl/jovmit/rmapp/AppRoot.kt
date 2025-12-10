package nl.jovmit.rmapp

import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import nl.jovmit.rmapp.ui.artdetails.artWorkDetailsScreen
import nl.jovmit.rmapp.ui.artdetails.navigateToArtWorkDetails
import nl.jovmit.rmapp.ui.artworks.ArtWorksDestination
import nl.jovmit.rmapp.ui.artworks.artWorksScreen

@Composable
fun AppRoot() {
  val navBackStack = rememberNavBackStack(ArtWorksDestination)

  NavDisplay(
    modifier = Modifier.fillMaxSize(),
    backStack = navBackStack,
    onBack = { navBackStack.removeLastOrNull() },
    entryProvider = entryProvider {
      artWorksScreen(
        onNavigateToArtWorkDetails = navBackStack::navigateToArtWorkDetails
      )
      artWorkDetailsScreen(
        onNavigateBack = navBackStack::removeLastOrNull
      )
    },
    transitionSpec = {
      slideInHorizontally(initialOffsetX = { it }) togetherWith
        scaleOut(targetScale = .9f)
    },
    popTransitionSpec = {
      scaleIn(initialScale = .9f) togetherWith
        slideOutHorizontally(targetOffsetX = { it })
    },
    predictivePopTransitionSpec = {
      scaleIn(initialScale = .9f) togetherWith
        slideOutHorizontally(targetOffsetX = { it })
    }
  )
}