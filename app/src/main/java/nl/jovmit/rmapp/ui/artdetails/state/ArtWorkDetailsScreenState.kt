package nl.jovmit.rmapp.ui.artdetails.state

import nl.jovmit.rmapp.domain.ArtWork

data class ArtWorkDetailsScreenState(
  val isLoading: Boolean = false,
  val artWork: ArtWork? = null,
  val artWorkNotFoundError: Boolean = false,
  val artWorkLoadingError: Boolean = false,
  val offlineError: Boolean = false
)