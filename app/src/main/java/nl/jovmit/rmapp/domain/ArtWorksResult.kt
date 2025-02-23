package nl.jovmit.rmapp.domain

sealed class ArtWorksResult {

  data class ArtWorksList(val artWorks: List<ArtWork>) : ArtWorksResult()

  data object ErrorLoadingArtWorks : ArtWorksResult()

  data object OfflineError : ArtWorksResult()
}