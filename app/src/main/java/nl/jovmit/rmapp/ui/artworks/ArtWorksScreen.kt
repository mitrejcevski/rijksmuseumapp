package nl.jovmit.rmapp.ui.artworks

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.collections.immutable.persistentListOf
import nl.jovmit.rmapp.R
import nl.jovmit.rmapp.domain.ArtWork
import nl.jovmit.rmapp.ui.artworks.state.ArtWorksScreenState
import nl.jovmit.rmapp.ui.composables.ErrorUI
import nl.jovmit.rmapp.ui.composables.ListLoadingShimmer
import nl.jovmit.rmapp.ui.composables.ToolbarTitle
import nl.jovmit.rmapp.ui.composables.toDp
import nl.jovmit.rmapp.ui.theme.AppTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun ArtWorksScreen(
  viewModel: ArtWorksViewModel = koinViewModel(),
  onNavigateToArtWorkDetails: (objectNumber: String) -> Unit
) {
  val screenState by viewModel.screenState.collectAsStateWithLifecycle()

  LaunchedEffect(Unit) {
    viewModel.loadArtWorksList()
  }

  ArtWorksScreenContent(
    screenState = screenState,
    onArtWorkClicked = onNavigateToArtWorkDetails
  )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun ArtWorksScreenContent(
  screenState: ArtWorksScreenState,
  onArtWorkClicked: (objectNumber: String) -> Unit
) {
  Scaffold(
    containerColor = MaterialTheme.colorScheme.background,
    topBar = {
      CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = MaterialTheme.colorScheme.background
        ),
        title = {
          ToolbarTitle(value = stringResource(R.string.label_art_works))
        }
      )
    }
  ) { paddingValues ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues),
      contentAlignment = Alignment.Center
    ) {
      if (screenState.isLoading) {
        ListLoadingShimmer(
          modifier = Modifier.fillMaxSize(),
          listItemsCount = 5
        )
      } else if (screenState.isErrorLoadingArtWorks) {
        ErrorUI(
          icon = painterResource(R.drawable.ic_loading_error),
          title = stringResource(R.string.label_error_title),
          message = stringResource(R.string.label_error_loading_artworks)
        )
      } else if (screenState.isOfflineError) {
        ErrorUI(
          icon = painterResource(R.drawable.ic_connection_error),
          title = stringResource(R.string.label_error_title),
          message = stringResource(R.string.label_connection_error)
        )
      } else {
        ArtWorksList(
          modifier = Modifier.fillMaxSize(),
          screenState = screenState,
          onArtWorkClicked = onArtWorkClicked
        )
      }
    }
  }
}

@Composable
private fun ArtWorksList(
  modifier: Modifier = Modifier,
  screenState: ArtWorksScreenState,
  onArtWorkClicked: (objectNumber: String) -> Unit
) {
  LazyVerticalGrid(
    modifier = modifier,
    columns = GridCells.Fixed(2),
    horizontalArrangement = Arrangement.spacedBy(AppTheme.size.normal),
    verticalArrangement = Arrangement.spacedBy(AppTheme.size.normal)
  ) {
    items(items = screenState.artWorks, key = { it.id }) { item ->
      ArtWorkListItem(
        modifier = Modifier
          .fillMaxWidth()
          .clickable { onArtWorkClicked(item.objectNumber) },
        artWork = item
      )
    }
  }
}

@Composable
private fun ArtWorkListItem(
  modifier: Modifier = Modifier,
  artWork: ArtWork
) {
  Column(modifier = modifier) {
    Box {
      AsyncImage(
        modifier = Modifier
          .fillMaxWidth(),
        model = ImageRequest.Builder(LocalContext.current)
          .data(artWork.webImage?.url)
          .crossfade(true)
          .build(),
        contentScale = ContentScale.FillWidth,
        contentDescription = artWork.longTitle,
      )
      Text(
        modifier = Modifier
          .align(Alignment.BottomStart)
          .fillMaxWidth()
          .background(MaterialTheme.colorScheme.onBackground.copy(.4f))
          .padding(horizontal = 12.dp, vertical = 8.dp),
        text = artWork.title,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        color = MaterialTheme.colorScheme.background
      )
    }
    HorizontalDivider()
  }
}

@Composable
@PreviewLightDark
private fun ArtWorksScreenLoadingPreview() {
  AppTheme {
    ArtWorksScreenContent(
      screenState = ArtWorksScreenState(isLoading = true),
      onArtWorkClicked = {}
    )
  }
}

@Composable
@PreviewLightDark
private fun ArtWorksScreenPreview() {
  AppTheme {
    ArtWorksScreenContent(
      screenState = ArtWorksScreenState(
        artWorks = persistentListOf(
          artWork("1", 1920, 460),
          artWork("2", 1904, 457),
          artWork("3", 1925, 461),
        )
      ),
      onArtWorkClicked = {}
    )
  }
}

@Composable
@PreviewLightDark
private fun ArtWorkScreenLoadingError() {
  AppTheme {
    ArtWorksScreenContent(
      screenState = ArtWorksScreenState(isErrorLoadingArtWorks = true),
      onArtWorkClicked = {}
    )
  }
}

@Composable
@PreviewLightDark
private fun ArtWorkScreenOfflineError() {
  AppTheme {
    ArtWorksScreenContent(
      screenState = ArtWorksScreenState(isOfflineError = true),
      onArtWorkClicked = {}
    )
  }
}

private fun artWork(id: String, width: Int, height: Int): ArtWork {
  return ArtWork(
    links = ArtWork.ArtWorkLinks("", "", ""),
    id = id,
    headerImage = ArtWork.Image(
      id = "",
      width = width,
      height = height,
      url = ""
    ),
    objectNumber = "",
    title = "Short Art Work Title",
    longTitle = "",
    webImage = null,
    productionPlaces = persistentListOf()
  )
}

