package nl.jovmit.rmapp.domain

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import nl.jovmit.rmapp.domain.ArtWorkBuilder.Companion.anArtWork
import org.junit.jupiter.api.Test

abstract class LoadArtWorksContractTest {

  private val nature = anArtWork()
    .withObjectNumber("NAT-01")
    .build()
  private val sea = anArtWork()
    .withObjectNumber("SEA-02")
    .build()
  private val urban = anArtWork()
    .withObjectNumber("URB-03")
    .build()
  private val allArtWorks = listOf(nature, sea, urban)

  @Test
  fun noArtWorksFound() = runTest {
    val noArtWorks = emptyList<ArtWork>()
    val repository = artWorksRepositoryWith(noArtWorks)

    val result = repository.loadArtWorks()

    assertThat(result).isEqualTo(ArtWorksResult.ArtWorksList(noArtWorks))
  }

  @Test
  fun artWorksFound() = runTest {
    val repository = artWorksRepositoryWith(allArtWorks)

    val result = repository.loadArtWorks()

    assertThat(result).isEqualTo(ArtWorksResult.ArtWorksList(allArtWorks))
  }

  @Test
  fun artWorkDetailsLoaded() = runTest {
    val repository = artWorksRepositoryWith(allArtWorks)

    val result = repository.loadArtWorkDetails(nature.objectNumber)

    assertThat(result).isEqualTo(ArtWorkDetailsResult.Item(nature))
  }

  @Test
  fun errorLoadingArtWorks() = runTest {
    val repository = unavailableArtWorksRepository()

    val result = repository.loadArtWorks()

    assertThat(result).isEqualTo(ArtWorksResult.ErrorLoadingArtWorks)
  }

  @Test
  fun offlineLoadingArtWork() = runTest {
    val repository = offlineArtWorksRepository()

    val result = repository.loadArtWorks()

    assertThat(result).isEqualTo(ArtWorksResult.OfflineError)
  }

  @Test
  fun artWorkNotFound() = runTest {
    val repository = artWorksRepositoryWith(allArtWorks)

    val result = repository.loadArtWorkDetails("anythingBut${sea.objectNumber}")

    assertThat(result).isEqualTo(ArtWorkDetailsResult.ArtWorkNotFound)
  }

  @Test
  fun errorLoadingArtWorkDetails() = runTest {
    val repository = unavailableArtWorksRepository()

    val result = repository.loadArtWorkDetails(":irrelevant:")

    assertThat(result).isEqualTo(ArtWorkDetailsResult.ErrorLoadingArtWork)
  }

  @Test
  fun offlineLoadingArtWorkDetails() = runTest {
    val repository = offlineArtWorksRepository()

    val result = repository.loadArtWorkDetails(":irrelevant:")

    assertThat(result).isEqualTo(ArtWorkDetailsResult.OfflineLoadingArtWork)
  }

  abstract fun artWorksRepositoryWith(artWorks: List<ArtWork>): ArtWorksRepository

  abstract fun unavailableArtWorksRepository(): ArtWorksRepository

  abstract fun offlineArtWorksRepository(): ArtWorksRepository
}