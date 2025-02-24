package nl.jovmit.rmapp.ui.artdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import nl.jovmit.rmapp.domain.ArtWork
import nl.jovmit.rmapp.domain.ArtWorkDetailsResult
import nl.jovmit.rmapp.domain.ArtWorksRepository
import nl.jovmit.rmapp.ui.artdetails.state.ArtWorkDetailsScreenState

class ArtWorkDetailsViewModel(
  private val artWorksRepository: ArtWorksRepository,
  private val backgroundDispatcher: CoroutineDispatcher,
  savedStateHandle: SavedStateHandle
) : ViewModel() {

  private val objectNumber = savedStateHandle.get<String>("objectNumber") ?: ""

  private val _screenState = MutableStateFlow(ArtWorkDetailsScreenState())
  val screenState: StateFlow<ArtWorkDetailsScreenState> = _screenState

  fun loadArtWorkDetails() {
    viewModelScope.launch {
      setLoading()
      val result = withContext(backgroundDispatcher) {
        artWorksRepository.loadArtWorkDetails(objectNumber)
      }
      onArtWorkDetailsResult(result)
    }
  }

  private fun onArtWorkDetailsResult(result: ArtWorkDetailsResult) {
    when (result) {
      is ArtWorkDetailsResult.Item -> onArtWorkLoaded(result.artWork)
      is ArtWorkDetailsResult.ArtWorkNotFound -> onArtWorkNotFoundError()
      is ArtWorkDetailsResult.ErrorLoadingArtWork -> onErrorLoadingArtWork()
      is ArtWorkDetailsResult.OfflineLoadingArtWork -> onOfflineError()
    }
  }

  private fun setLoading() {
    _screenState.update { it.copy(isLoading = true) }
  }

  private fun onArtWorkLoaded(artWork: ArtWork) {
    _screenState.update { it.copy(isLoading = false, artWork = artWork) }
  }

  private fun onArtWorkNotFoundError() {
    _screenState.update { it.copy(isLoading = false, artWorkNotFoundError = true) }
  }

  private fun onErrorLoadingArtWork() {
    _screenState.update { it.copy(isLoading = false, artWorkLoadingError = true) }
  }

  private fun onOfflineError() {
    _screenState.update { it.copy(isLoading = false, offlineError = true) }
  }
}
