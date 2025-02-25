package nl.jovmit.rmapp.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode

class MuseumApi(
  private val httpClient: HttpClient
) {

  suspend fun getArtWorksList(): ArtWorksResponse {
    val response = httpClient.get("api/en/collection")
    return if (response.status == HttpStatusCode.OK) {
      response.body()
    } else {
      throw HttpException(response.status.value)
    }
  }

  suspend fun getArtWorkDetails(objectNumber: String): ArtWorkDetailsResponse {
    val response = httpClient.get("api/en/collection/$objectNumber")
    return if (response.status == HttpStatusCode.OK) {
      response.body()
    } else {
      throw HttpException(response.status.value)
    }
  }
}