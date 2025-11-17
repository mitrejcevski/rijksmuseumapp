package nl.jovmit.rmapp

import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.scene.SinglePaneSceneStrategy
import androidx.navigation3.ui.NavDisplay
import kotlinx.serialization.Serializable
import nl.jovmit.rmapp.ui.BottomSheetSceneStrategy
import nl.jovmit.rmapp.ui.artdetails.ArtWorkDetailsScreen
import nl.jovmit.rmapp.ui.artdetails.ArtworkDetailsDestination
import nl.jovmit.rmapp.ui.artworks.ArtWorksDestination
import nl.jovmit.rmapp.ui.artworks.ArtWorksScreen
import nl.jovmit.rmapp.ui.composables.CustomBottomSheet

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun AppRoot() {
  val navBackStack = rememberNavBackStack(ArtWorksDestination)
  val bottomSheetSceneStrategy = remember { BottomSheetSceneStrategy<NavKey>() }
  val oneMoreStrategy = remember { SinglePaneSceneStrategy<NavKey>() }

  NavDisplay(
    modifier = Modifier.fillMaxSize(),
    backStack = navBackStack,
    sceneStrategy = bottomSheetSceneStrategy.then(oneMoreStrategy),
    onBack = { navBackStack.removeLastOrNull() },
    entryProvider = entryProvider {

      entry<ArtWorksDestination> {
        ArtWorksScreen(
          onNavigateToArtWorkDetails = { navBackStack.add(BottomSheetDestination) }
        )
      }

      entry<ArtworkDetailsDestination> {
        ArtWorkDetailsScreen(
          onNavigateBack = { navBackStack.removeLastOrNull() }
        )
      }

      entry<BottomSheetDestination>(
        metadata = BottomSheetSceneStrategy.bottomSheet()
      ) {
        CustomBottomSheet(onDismissed = navBackStack::removeLastOrNull)
      }
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

@Serializable
data object BottomSheetDestination : NavKey