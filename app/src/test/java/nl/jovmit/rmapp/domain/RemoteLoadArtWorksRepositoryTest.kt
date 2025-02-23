package nl.jovmit.rmapp.domain

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import nl.jovmit.rmapp.network.ArtObject
import nl.jovmit.rmapp.network.ArtWorkDetailsResponse
import nl.jovmit.rmapp.network.ArtWorksResponse
import nl.jovmit.rmapp.network.MuseumApi
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException

class RemoteLoadArtWorksRepositoryTest : LoadArtWorksContractTest() {

  private val mockWebServer = MockWebServer()
  private val json = Json { ignoreUnknownKeys = true }
  private val retrofit = Retrofit.Builder()
    .baseUrl(mockWebServer.url("/"))
    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
    .build()
  private val museumApi = retrofit.create(MuseumApi::class.java)

  override fun artWorksRepositoryWith(artWorks: List<ArtWork>): ArtWorksRepository {
    mockWebServer.dispatcher = CustomDispatcher(artWorks)
    return RemoteArtWorksRepository(museumApi)
  }

  override fun unavailableArtWorksRepository(): ArtWorksRepository {
    val unavailableApi = UnavailableMuseumApi()
    return RemoteArtWorksRepository(unavailableApi)
  }

  override fun offlineArtWorksRepository(): ArtWorksRepository {
    val unavailableApi = UnavailableMuseumApi(isOffline = true)
    return RemoteArtWorksRepository(unavailableApi)
  }

  private class CustomDispatcher(
    private val artWorks: List<ArtWork>
  ) : Dispatcher() {

    override fun dispatch(request: RecordedRequest): MockResponse {
      return if (request.isArtWorksRequest()) {
        MockResponse().setResponseCode(200).setBody(artWorks.toResponseBody())
      } else if (request.isArtWorkDetailsRequest()) {
        val objectNumber = request.requestUrl?.pathSegments?.last() ?: ""
        val match = artWorks.find { it.objectNumber == objectNumber }
        if (match == null) {
          MockResponse().setResponseCode(404)
        } else {
          val responseEntity = Json.encodeToString(match.toResponseEntity())
          val body = buildString {
            append("{")
            append(""""artObject": $responseEntity """)
            append("}")
          }
          MockResponse().setResponseCode(200).setBody(body)
        }
      } else {
        MockResponse().setResponseCode(404)
      }
    }

    private fun List<ArtWork>.toResponseBody(): String {
      val entities = Json.encodeToString(this.map { it.toResponseEntity() })
      return buildString {
        append("{")
        append(""""artObjects": $entities """)
        append("}")
      }
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

    private fun RecordedRequest.isArtWorksRequest(): Boolean {
      return method == "GET" && requestUrl?.pathSegments?.size == 3
    }

    private fun RecordedRequest.isArtWorkDetailsRequest(): Boolean {
      return method == "GET" && requestUrl?.pathSegments?.size == 4
    }
  }

  private class UnavailableMuseumApi(
    private val isOffline: Boolean = false
  ) : MuseumApi {

    override suspend fun getArtWorksList(): ArtWorksResponse {
      throw if (isOffline) IOException()
      else throw HttpException(Response.error<String>(400, "error".toResponseBody()))
    }

    override suspend fun getArtWorkDetails(objectNumber: String): ArtWorkDetailsResponse {
      throw if (isOffline) IOException()
      else throw HttpException(Response.error<String>(400, "error".toResponseBody()))
    }
  }
}