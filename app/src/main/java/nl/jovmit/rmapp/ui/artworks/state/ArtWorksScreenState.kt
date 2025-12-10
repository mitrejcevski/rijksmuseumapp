package nl.jovmit.rmapp.ui.artworks.state

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import nl.jovmit.rmapp.domain.ArtWork

data class ArtWorksScreenState(
  val isLoading: Boolean = false,
  val artWorks: ImmutableList<ArtWork> = persistentListOf(),
  val isErrorLoadingArtWorks: Boolean = false,
  val isOfflineError: Boolean = false
)