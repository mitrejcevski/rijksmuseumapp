package nl.jovmit.rmapp.ui.artdetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import nl.jovmit.rmapp.R
import nl.jovmit.rmapp.ui.composables.ToolbarTitle
import nl.jovmit.rmapp.ui.theme.RMAppTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun ArtWorkDetailsScreen(
  viewModel: ArtWorkDetailsViewModel = koinViewModel(),
  onNavigateBack: () -> Unit
) {

  ArtWorkDetailsScreenContent(
    onNavigateBack = onNavigateBack
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ArtWorkDetailsScreenContent(
  onNavigateBack: () -> Unit
) {
  Scaffold(
    containerColor = MaterialTheme.colorScheme.background,
    topBar = {
      CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = MaterialTheme.colorScheme.background
        ),
        title = {
          ToolbarTitle(value = "")
        },
        navigationIcon = {
          IconButton(onClick = onNavigateBack) {
            Icon(
              painter = painterResource(R.drawable.ic_arrow_left),
              contentDescription = stringResource(R.string.cd_navigate_up)
            )
          }
        }
      )
    }
  ) { contentPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(contentPadding)
    ) {

    }
  }
}

@Composable
@PreviewLightDark
private fun PreviewArtWorkDetailsScreen() {
  RMAppTheme {
    ArtWorkDetailsScreenContent(
      onNavigateBack = {}
    )
  }
}