package nl.jovmit.rmapp.domain

class InMemoryArtWorksRepository(
  private val artWorks: List<ArtWork> = emptyList()
) : ArtWorksRepository {

  private var isUnavailable = false
  private var isOffline = false

  override suspend fun loadArtWorks(): ArtWorksResult {
    if (isUnavailable) return ArtWorksResult.ErrorLoadingArtWorks
    if (isOffline) return ArtWorksResult.OfflineError

    return ArtWorksResult.ArtWorksList(artWorks)
  }

  override suspend fun loadArtWorkDetails(objectNumber: String): ArtWorkDetailsResult {
    if (isUnavailable) return ArtWorkDetailsResult.ErrorLoadingArtWork
    if (isOffline) return ArtWorkDetailsResult.OfflineLoadingArtWork

    val artWork = artWorks.find { it.objectNumber == objectNumber }
    return artWork?.let { ArtWorkDetailsResult.Item(it) }
      ?: ArtWorkDetailsResult.ArtWorkNotFound
  }

  fun setUnavailable() {
    isUnavailable = true
  }

  fun setOffline() {
    isOffline = true
  }
}