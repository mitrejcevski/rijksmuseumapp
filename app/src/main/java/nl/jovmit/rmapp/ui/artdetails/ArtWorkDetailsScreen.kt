package nl.jovmit.rmapp.ui.artdetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import nl.jovmit.rmapp.R
import nl.jovmit.rmapp.domain.ArtWork
import nl.jovmit.rmapp.ui.artdetails.state.ArtWorkDetailsScreenState
import nl.jovmit.rmapp.ui.composables.ArtWorkTitle
import nl.jovmit.rmapp.ui.composables.ErrorUI
import nl.jovmit.rmapp.ui.composables.ListItemLabel
import nl.jovmit.rmapp.ui.composables.ShimmerItem
import nl.jovmit.rmapp.ui.composables.ToolbarTitle
import nl.jovmit.rmapp.ui.composables.toDp
import nl.jovmit.rmapp.ui.theme.RMAppTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun ArtWorkDetailsScreen(
  viewModel: ArtWorkDetailsViewModel = koinViewModel(),
  onNavigateBack: () -> Unit
) {
  val state by viewModel.screenState.collectAsStateWithLifecycle()

  LaunchedEffect(Unit) {
    viewModel.loadArtWorkDetails()
  }

  ArtWorkDetailsScreenContent(
    screenState = state,
    onNavigateBack = onNavigateBack
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ArtWorkDetailsScreenContent(
  screenState: ArtWorkDetailsScreenState,
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
          ToolbarTitle(value = screenState.artWork?.title ?: "")
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
        .padding(contentPadding),
      contentAlignment = Alignment.Center
    ) {
      if (screenState.isLoading) {
        LoadingShimmer(
          modifier = Modifier.fillMaxSize()
        )
      } else if (screenState.artWorkNotFoundError) {
        ErrorUI(
          icon = painterResource(R.drawable.ic_error_missing),
          title = stringResource(R.string.label_error_title),
          message = stringResource(R.string.error_art_work_not_found)
        )
      } else if (screenState.artWorkLoadingError) {
        ErrorUI(
          icon = painterResource(R.drawable.ic_loading_error),
          title = stringResource(R.string.label_error_title),
          message = stringResource(R.string.label_error_loading_artwork_details)
        )
      } else if (screenState.offlineError) {
        ErrorUI(
          icon = painterResource(R.drawable.ic_connection_error),
          title = stringResource(R.string.label_error_title),
          message = stringResource(R.string.label_connection_error)
        )
      } else {
        ArtWorkDetails(
          modifier = Modifier.fillMaxSize(),
          artWork = screenState.artWork
        )
      }
    }
  }
}

@Composable
private fun LoadingShimmer(
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier,
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    ShimmerItem(
      modifier = Modifier
        .fillMaxWidth()
        .aspectRatio(1f),
    )
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 12.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      ShimmerItem(
        modifier = Modifier
          .width(250.dp)
          .height(32.dp)
      )
      ShimmerItem(
        modifier = Modifier
          .width(150.dp)
          .height(24.dp)
      )
    }
  }
}

@Composable
private fun ArtWorkDetails(
  modifier: Modifier = Modifier,
  artWork: ArtWork?
) {
  val scroll = rememberScrollState()
  Column(
    modifier = modifier.verticalScroll(scroll),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    AsyncImage(
      modifier = Modifier
        .fillMaxWidth()
        .aspectRatio(artWork?.headerImage?.aspectRatio ?: 1f)
        .height(artWork?.headerImage?.height?.toDp() ?: 0.dp),
      model = ImageRequest.Builder(LocalContext.current)
        .data(artWork?.webImage?.url)
        .crossfade(true)
        .build(),
      contentScale = ContentScale.Crop,
      contentDescription = artWork?.longTitle
    )
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 12.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      ArtWorkTitle(value = artWork?.longTitle ?: "")
      artWork?.productionPlaces?.forEach {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
          Icon(
            painter = painterResource(R.drawable.ic_location),
            contentDescription = stringResource(R.string.cd_production_place),
            tint = MaterialTheme.colorScheme.onBackground
          )
          ListItemLabel(value = it)
        }
      }
    }
  }
}

@Composable
@PreviewLightDark
private fun PreviewArtWorkDetailsScreenLoading() {
  RMAppTheme {
    ArtWorkDetailsScreenContent(
      screenState = ArtWorkDetailsScreenState(
        isLoading = true
      ),
      onNavigateBack = {}
    )
  }
}

@Composable
@PreviewLightDark
private fun PreviewArtWorkNotFoundDetailsScreen() {
  RMAppTheme {
    ArtWorkDetailsScreenContent(
      screenState = ArtWorkDetailsScreenState(
        artWorkNotFoundError = true
      ),
      onNavigateBack = {}
    )
  }
}

@Composable
@PreviewLightDark
private fun PreviewArtWorkDetailsScreen() {
  RMAppTheme {
    ArtWorkDetailsScreenContent(
      screenState = ArtWorkDetailsScreenState(
        artWork = ArtWork(
          links = ArtWork.ArtWorkLinks("", "", ""),
          id = "",
          headerImage = null,
          objectNumber = "",
          title = "Short Art Work Title",
          longTitle = "Long Art Work Title Going Here",
          webImage = ArtWork.Image(
            id = "",
            width = 8176,
            height = 6132,
            url = ""
          ),
          productionPlaces = listOf("Haarlem, The Netherlands")
        )
      ),
      onNavigateBack = {}
    )
  }
}

@Composable
@PreviewLightDark
private fun PreviewArtWorkDetailsScreenUnavailableError() {
  RMAppTheme {
    ArtWorkDetailsScreenContent(
      screenState = ArtWorkDetailsScreenState(
        artWorkLoadingError = true
      ),
      onNavigateBack = {}
    )
  }
}

@Composable
@PreviewLightDark
private fun PreviewArtWorkDetailsScreenOfflineError() {
  RMAppTheme {
    ArtWorkDetailsScreenContent(
      screenState = ArtWorkDetailsScreenState(
        offlineError = true
      ),
      onNavigateBack = {}
    )
  }
}