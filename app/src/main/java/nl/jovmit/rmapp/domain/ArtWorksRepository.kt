package nl.jovmit.rmapp.domain

interface ArtWorksRepository {

  suspend fun loadArtWorks(): ArtWorksResult

  suspend fun loadArtWorkDetails(objectNumber: String): ArtWorkDetailsResult
}