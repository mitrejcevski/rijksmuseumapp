package nl.jovmit.rmapp.domain

class InMemoryLoadArtWorksTest : LoadArtWorksContractTest() {

  override fun artWorksRepositoryWith(artWorks: List<ArtWork>): ArtWorksRepository {
    return InMemoryArtWorksRepository(artWorks = artWorks)
  }

  override fun unavailableArtWorksRepository(): ArtWorksRepository {
    return InMemoryArtWorksRepository().apply { setUnavailable() }
  }

  override fun offlineArtWorksRepository(): ArtWorksRepository {
    return InMemoryArtWorksRepository().apply { setOffline() }
  }
}