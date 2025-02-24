package nl.jovmit.rmapp.ui.artworks.state

import nl.jovmit.rmapp.domain.ArtWork

data class ArtWorksScreenState(
  val isLoading: Boolean = false,
  val artWorks: List<ArtWork> = emptyList(),
  val isErrorLoadingArtWorks: Boolean = false,
  val isOfflineError: Boolean = false
)