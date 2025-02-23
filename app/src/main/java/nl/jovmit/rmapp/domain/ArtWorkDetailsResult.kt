package nl.jovmit.rmapp.domain

sealed class ArtWorkDetailsResult {

  data class Item(val artWork: ArtWork) : ArtWorkDetailsResult()

  data object ArtWorkNotFound : ArtWorkDetailsResult()

  data object ErrorLoadingArtWork : ArtWorkDetailsResult()

  data object OfflineLoadingArtWork : ArtWorkDetailsResult()
}