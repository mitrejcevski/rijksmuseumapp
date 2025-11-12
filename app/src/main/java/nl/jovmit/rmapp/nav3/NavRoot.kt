package nl.jovmit.rmapp.nav3

import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay

@Composable
fun NavRoot(
  deepLink: String?
) {
  val backStack = rememberNavBackStack(WelcomeDestination)
  NavDisplay(
    modifier = Modifier.fillMaxSize(),
    backStack = backStack,
    entryProvider = entryProvider {
      entry<WelcomeDestination> {
        Screen(
          label = "Welcome",
          color = Color.Blue.copy(.3f),
          onNavigate = {
            backStack.add(MainDestination.MainRoot)
            backStack.removeFirstOrNull()
          }
        )
      }
      entry<MainDestination.MainRoot> {
        MainScreen(
          openHomeItemDetails = {
            backStack.add(HomeItemDetails(it))
          }
        )
      }
      entry<HomeItemDetails> {
        Screen(
          label = "Home Item Details ${it.itemId}",
          color = Color.Red,
          onNavigate = {},
          onBack = { backStack.removeLastOrNull() })
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
