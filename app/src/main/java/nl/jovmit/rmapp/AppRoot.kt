package nl.jovmit.rmapp

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable

@Composable
fun AppRoot() {
  val navController = rememberNavController()

  SlidingNavHost(
    modifier = Modifier.fillMaxSize(),
    navController = navController,
    startDestination = FirstScreen,
  ) {

    composable<FirstScreen> {
      Screen(
        label = "First Screen",
        backgroundColor = Color.Red,
        onClick = { navController.navigate(SecondScreen) }
      )
    }

    composable<SecondScreen> {
      Screen(
        label = "Second Screen",
        backgroundColor = Color.Blue,
        onClick = { navController.navigateUp() }
      )
    }
  }
}

@Composable
fun SlidingNavHost(
  modifier: Modifier = Modifier,
  navController: NavHostController,
  startDestination: Any,
  builder: NavGraphBuilder.() -> Unit
) {
  NavHost(
    modifier = modifier,
    navController = navController,
    startDestination = startDestination,
    enterTransition = {
      slideIntoContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.Left,
        animationSpec = tween(durationMillis = 2000)
      )
    },
    exitTransition = {
      scaleOut(
        targetScale = .9f,
        animationSpec = tween(durationMillis = 1000)
      )
    },
    popEnterTransition = {
      scaleIn(
        initialScale = .9f,
        animationSpec = tween(durationMillis = 1000)
      )
    },
    popExitTransition = {
      slideOutOfContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.Right,
        animationSpec = tween(durationMillis = 2000)
      )
    },
    builder = builder
  )
}

@Serializable
private data object FirstScreen

@Serializable
private data object SecondScreen

@Composable
private fun Screen(
  label: String,
  backgroundColor: Color,
  onClick: () -> Unit
) {
  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(backgroundColor),
    contentAlignment = Alignment.Center
  ) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      Text(
        text = label,
        color = Color.White
      )
      Button(
        onClick = onClick
      ) {
        Text(text = "Click To Navigate")
      }
    }
  }
}