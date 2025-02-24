package nl.jovmit.rmapp.artdetails

import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import nl.jovmit.rmapp.CoroutineTestExtension
import nl.jovmit.rmapp.domain.ArtWorkBuilder.Companion.anArtWork
import nl.jovmit.rmapp.domain.InMemoryArtWorksRepository
import nl.jovmit.rmapp.observeFlow
import nl.jovmit.rmapp.ui.artdetails.ArtWorkDetailsViewModel
import nl.jovmit.rmapp.ui.artdetails.state.ArtWorkDetailsScreenState
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.UUID

@ExtendWith(CoroutineTestExtension::class)
class LoadArtDetailsTest {

  private val urban = anArtWork()
    .withObjectNumber(UUID.randomUUID().toString())
    .build()

  private val stateHandle = SavedStateHandle().apply {
    set("objectNumber", urban.objectNumber)
  }
  private val artWorksRepository = InMemoryArtWorksRepository(
    artWorks = listOf(urban)
  )
  private val dispatcher = Dispatchers.Unconfined

  @Test
  fun artWorkNotFound() {
    val repository = InMemoryArtWorksRepository(artWorks = emptyList())
    val viewModel = ArtWorkDetailsViewModel(repository, dispatcher, stateHandle)

    viewModel.loadArtWorkDetails()

    assertThat(viewModel.screenState.value)
      .isEqualTo(ArtWorkDetailsScreenState(artWorkNotFoundError = true))
  }

  @Test
  fun artWorkDetailsLoaded() {
    val viewModel = ArtWorkDetailsViewModel(artWorksRepository, dispatcher, stateHandle)

    viewModel.loadArtWorkDetails()

    assertThat(viewModel.screenState.value)
      .isEqualTo(ArtWorkDetailsScreenState(artWork = urban))
  }

  @Test
  fun errorLoadingArtWorkDetails() {
    val repository = InMemoryArtWorksRepository().apply { setUnavailable() }
    val viewModel = ArtWorkDetailsViewModel(repository, dispatcher, stateHandle)

    viewModel.loadArtWorkDetails()

    assertThat(viewModel.screenState.value)
      .isEqualTo(ArtWorkDetailsScreenState(artWorkLoadingError = true))
  }

  @Test
  fun offlineLoadingArtWorkDetails() {
    val repository = InMemoryArtWorksRepository().apply { setOffline() }
    val viewModel = ArtWorkDetailsViewModel(repository, dispatcher, stateHandle)

    viewModel.loadArtWorkDetails()

    assertThat(viewModel.screenState.value)
      .isEqualTo(ArtWorkDetailsScreenState(offlineError = true))
  }

  @Test
  fun artWorkDetailsStateDeliveredInOrder() = runTest {
    val viewModel = ArtWorkDetailsViewModel(artWorksRepository, dispatcher, stateHandle)

    val deliveredStates = observeFlow(viewModel.screenState) {
      viewModel.loadArtWorkDetails()
    }

    assertThat(deliveredStates).isEqualTo(
      listOf(
        ArtWorkDetailsScreenState(isLoading = true),
        ArtWorkDetailsScreenState(artWork = urban)
      )
    )
  }
}