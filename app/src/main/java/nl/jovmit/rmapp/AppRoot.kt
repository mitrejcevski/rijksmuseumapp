package nl.jovmit.rmapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import nl.jovmit.rmapp.ui.artworks.ArtWorksDestination
import nl.jovmit.rmapp.ui.artworks.artWorksScreen

@Composable
fun AppRoot() {
  val navController = rememberNavController()
  NavHost(
    modifier = Modifier.fillMaxSize(),
    navController = navController,
    startDestination = ArtWorksDestination
  ) {
    artWorksScreen(
      onNavigateToArtWorkDetails = {}
    )
  }
}