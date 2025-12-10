package nl.jovmit.rmapp.ui.artworks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import nl.jovmit.rmapp.domain.ArtWorksRepository
import nl.jovmit.rmapp.domain.ArtWorksResult
import nl.jovmit.rmapp.ui.artworks.state.ArtWorksScreenState

class ArtWorksViewModel(
  private val artWorksRepository: ArtWorksRepository,
  private val backgroundDispatcher: CoroutineDispatcher
) : ViewModel() {

  private val _screenState = MutableStateFlow(ArtWorksScreenState())
  val screenState: StateFlow<ArtWorksScreenState> = _screenState

  fun loadArtWorksList() {
    if (screenState.value.artWorks.isEmpty()) {
      refresh()
    }
  }

  private fun refresh() {
    viewModelScope.launch {
      setLoading()
      val result = withContext(backgroundDispatcher) {
        artWorksRepository.loadArtWorks()
      }
      onArtWorksResult(result)
    }
  }

  private fun onArtWorksResult(result: ArtWorksResult) {
    when (result) {
      is ArtWorksResult.ArtWorksList -> onArtWorksLoaded(result)
      is ArtWorksResult.ErrorLoadingArtWorks -> onErrorLoadingArtWorks()
      is ArtWorksResult.OfflineError -> onOfflineError()
    }
  }

  private fun setLoading() {
    _screenState.update { it.copy(isLoading = true) }
  }

  private fun onArtWorksLoaded(result: ArtWorksResult.ArtWorksList) {
    _screenState.update { it.copy(isLoading = false, artWorks = result.artWorks.toPersistentList()) }
  }

  private fun onErrorLoadingArtWorks() {
    _screenState.update { it.copy(isLoading = false, isErrorLoadingArtWorks = true) }
  }

  private fun onOfflineError() {
    _screenState.update { it.copy(isLoading = false, isOfflineError = true) }
  }
}