package nl.jovmit.rmapp.domain

import nl.jovmit.rmapp.network.ArtObject
import nl.jovmit.rmapp.network.HttpException
import nl.jovmit.rmapp.network.MuseumApi
import java.io.IOException

class RemoteArtWorksRepository(
  private val museumApi: MuseumApi
) : ArtWorksRepository {

  override suspend fun loadArtWorks(): ArtWorksResult {
    return try {
      val response = museumApi.getArtWorksList()
      ArtWorksResult.ArtWorksList(response.artObjects.map { it.toArtWork() })
    } catch (httpException: HttpException) {
      ArtWorksResult.ErrorLoadingArtWorks
    } catch (ioException: IOException) {
      ArtWorksResult.OfflineError
    }
  }

  override suspend fun loadArtWorkDetails(objectNumber: String): ArtWorkDetailsResult {
    return try {
      val response = museumApi.getArtWorkDetails(objectNumber)
      ArtWorkDetailsResult.Item(response.artObject.toArtWork())
    } catch (httpException: HttpException) {
      loadingErrorFrom(httpException)
    } catch (offlineException: IOException) {
      ArtWorkDetailsResult.OfflineLoadingArtWork
    }
  }

  private fun ArtObject.toArtWork(): ArtWork {
    return ArtWork(
      links = ArtWork.ArtWorkLinks(
        self = links.self ?: "",
        web = links.web ?: "",
        search = links.search ?: ""
      ),
      id = id,
      objectNumber = objectNumber,
      title = title,
      longTitle = longTitle,
      webImage = webImage?.let {
        ArtWork.Image(
          id = it.guid,
          width = it.width,
          height = it.height,
          url = it.url
        )
      },
      headerImage = headerImage?.let {
        ArtWork.Image(
          id = it.guid,
          width = it.width,
          height = it.height,
          url = it.url
        )
      },
      productionPlaces = productionPlaces
    )
  }

  private fun loadingErrorFrom(httpException: HttpException): ArtWorkDetailsResult {
    return if (httpException.statusCode == 404) {
      ArtWorkDetailsResult.ArtWorkNotFound
    } else {
      ArtWorkDetailsResult.ErrorLoadingArtWork
    }
  }
}