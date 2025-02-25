package nl.jovmit.rmapp.domain

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.respondError
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.HttpRequestData
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import nl.jovmit.rmapp.network.ArtObject
import nl.jovmit.rmapp.network.MuseumApi
import java.io.IOException

class RemoteLoadArtWorksRepositoryTest : LoadArtWorksContractTest() {

  override fun artWorksRepositoryWith(artWorks: List<ArtWork>): ArtWorksRepository {
    val mockEngine = MockEngine { request ->
      val responseBody = responseBodyFor(artWorks, request)
      respond(content = responseBody.content, status = responseBody.statusCode)
    }
    val museumApi = MuseumApi(HttpClient(mockEngine) {
      install(ContentNegotiation) { json(Json) }
    })
    return RemoteArtWorksRepository(museumApi)
  }

  override fun unavailableArtWorksRepository(): ArtWorksRepository {
    val mockEngine = MockEngine { _ -> respondError(HttpStatusCode.BadRequest) }
    val museumApi = MuseumApi(HttpClient(mockEngine))
    return RemoteArtWorksRepository(museumApi)
  }

  override fun offlineArtWorksRepository(): ArtWorksRepository {
    val mockEngine = MockEngine { _ -> throw IOException() }
    val museumApi = MuseumApi(HttpClient(mockEngine))
    return RemoteArtWorksRepository(museumApi)
  }

  private fun responseBodyFor(
    artWorks: List<ArtWork>,
    request: HttpRequestData
  ): ResponseData {
    return if (request.isArtWorksRequest()) {
      ResponseData(HttpStatusCode.OK, artWorks.toResponseBody())
    } else if (request.isArtWorkDetailsRequest()) {
      val objectNumber = request.url.rawSegments.last()
      val match = artWorks.find { it.objectNumber == objectNumber }
      match?.let { ResponseData(HttpStatusCode.OK, match.toResponseBody()) }
        ?: ResponseData(HttpStatusCode.NotFound, "")
    } else {
      ResponseData(HttpStatusCode.NotFound, "")
    }
  }

  private fun HttpRequestData.isArtWorksRequest() : Boolean {
    return method == HttpMethod.Get && url.rawSegments.size == 3
  }

  private fun HttpRequestData.isArtWorkDetailsRequest() : Boolean {
    return method == HttpMethod.Get && url.rawSegments.size == 4
  }

  private fun List<ArtWork>.toResponseBody(): String {
    val entities = Json.encodeToString(this.map { it.toResponseEntity() })
    return """{"artObjects": $entities}"""
  }

  private fun ArtWork.toResponseBody(): String {
    val responseEntity = Json.encodeToString(this.toResponseEntity())
    return """{"artObjects": $responseEntity }"""
  }

  private fun ArtWork.toResponseEntity(): ArtObject {
    return ArtObject(
      links = ArtObject.Links(
        self = links.self,
        web = links.web,
        search = links.search
      ),
      id = id,
      objectNumber = objectNumber,
      title = title,
      longTitle = longTitle,
      headerImage = headerImage?.let {
        ArtObject.ImageEntity(
          guid = it.id,
          width = it.width,
          height = it.height,
          url = it.url
        )
      },
      webImage = webImage?.let {
        ArtObject.ImageEntity(
          guid = it.id,
          width = it.width,
          height = it.height,
          url = it.url
        )
      },
      productionPlaces = productionPlaces
    )
  }

  private data class ResponseData(
    val statusCode: HttpStatusCode,
    val content: String
  )
}