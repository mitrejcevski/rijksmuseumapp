package nl.jovmit.rmapp.artworks

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import nl.jovmit.rmapp.CoroutineTestExtension
import nl.jovmit.rmapp.domain.ArtWorkBuilder.Companion.anArtWork
import nl.jovmit.rmapp.domain.InMemoryArtWorksRepository
import nl.jovmit.rmapp.observeFlow
import nl.jovmit.rmapp.ui.artworks.ArtWorksViewModel
import nl.jovmit.rmapp.ui.artworks.state.ArtWorksScreenState
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(CoroutineTestExtension::class)
class LoadArtWorksTest {

  private val mountain = anArtWork()
    .withObjectNumber("MO-01")
    .build()
  private val river = anArtWork()
    .withObjectNumber("RI-02")
    .build()

  private val dispatcher = Dispatchers.Unconfined

  @Test
  fun noArtWorksLoaded() {
    val repository = InMemoryArtWorksRepository()
    val viewModel = ArtWorksViewModel(repository, dispatcher)

    viewModel.loadArtWorksList()

    assertThat(viewModel.screenState.value)
      .isEqualTo(ArtWorksScreenState(artWorks = emptyList()))
  }

  @Test
  fun artWorksLoaded() {
    val repository = InMemoryArtWorksRepository(listOf(mountain, river))
    val viewModel = ArtWorksViewModel(repository, dispatcher)

    viewModel.loadArtWorksList()

    assertThat(viewModel.screenState.value)
      .isEqualTo(ArtWorksScreenState(artWorks = listOf(mountain, river)))
  }

  @Test
  fun errorLoadingArtWorks() {
    val repository = InMemoryArtWorksRepository().apply { setUnavailable() }
    val viewModel = ArtWorksViewModel(repository, dispatcher)

    viewModel.loadArtWorksList()

    assertThat(viewModel.screenState.value)
      .isEqualTo(ArtWorksScreenState(isErrorLoadingArtWorks = true))
  }

  @Test
  fun offlineLoadingArtWorks() {
    val repository = InMemoryArtWorksRepository().apply { setOffline() }
    val viewModel = ArtWorksViewModel(repository, dispatcher)

    viewModel.loadArtWorksList()

    assertThat(viewModel.screenState.value)
      .isEqualTo(ArtWorksScreenState(isOfflineError = true))
  }

  @Test
  fun artWorksStateDeliveredInOrder() = runTest {
    val repository = InMemoryArtWorksRepository(listOf(mountain, river))
    val viewModel = ArtWorksViewModel(repository, dispatcher)

    val deliveredStates = observeFlow(viewModel.screenState) {
      viewModel.loadArtWorksList()
    }

    assertThat(deliveredStates).isEqualTo(
      listOf(
        ArtWorksScreenState(isLoading = true),
        ArtWorksScreenState(artWorks = listOf(mountain, river))
      )
    )
  }
}