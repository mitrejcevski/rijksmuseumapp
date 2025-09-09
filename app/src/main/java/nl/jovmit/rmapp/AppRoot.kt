package nl.jovmit.rmapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import nl.jovmit.rmapp.ui.artdetails.artWorkDetailsScreen
import nl.jovmit.rmapp.ui.artdetails.navigateToArtWorkDetails
import nl.jovmit.rmapp.ui.artworks.ArtWorksDestination
import nl.jovmit.rmapp.ui.artworks.artWorksScreen
import nl.jovmit.rmapp.ui.voice.SoundRecordingScreen

@Composable
fun AppRoot() {
  val navController = rememberNavController()
  NavHost(
    modifier = Modifier.fillMaxSize(),
    navController = navController,
    startDestination = "sound"
  ) {

    composable("sound") {
      SoundRecordingScreen()
    }

    artWorksScreen(
      onNavigateToArtWorkDetails = navController::navigateToArtWorkDetails
    )

    artWorkDetailsScreen(
      onNavigateBack = navController::navigateUp
    )
  }
}