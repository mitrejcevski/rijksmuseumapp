package nl.jovmit.rmapp

import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation3.ListDetailSceneStrategy
import androidx.compose.material3.adaptive.navigation3.rememberListDetailSceneStrategy
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import nl.jovmit.rmapp.ui.artdetails.ArtWorkDetailsScreen
import nl.jovmit.rmapp.ui.artdetails.ArtworkDetailsDestination
import nl.jovmit.rmapp.ui.artworks.ArtWorksDestination
import nl.jovmit.rmapp.ui.artworks.ArtWorksScreen

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
      entry<ArtWorksDestination>(
        metadata = ListDetailSceneStrategy.listPane(
          detailPlaceholder = {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
              Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                  modifier = Modifier.size(196.dp),
                  painter = painterResource(R.drawable.ic_empty_photo),
                  contentDescription = null
                )
                Text(text = "Pick an ArtWork!")
              }
            }
          }
        )
      ) {
        ArtWorksScreen(
          onNavigateToArtWorkDetails = { objectNumber ->
            val destination = ArtworkDetailsDestination(objectNumber)
            backStack.add(destination)
          }
        )
      }

      entry<ArtworkDetailsDestination>(
        metadata = ListDetailSceneStrategy.detailPane()
      ) { key ->
        ArtWorkDetailsScreen(objectNumber = key.objectNumber)
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
  )
}