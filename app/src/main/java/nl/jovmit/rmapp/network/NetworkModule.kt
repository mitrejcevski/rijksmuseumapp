package nl.jovmit.rmapp.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val networkModule = module {

  single<HttpClient> {
    HttpClient(Android) {
      install(ContentNegotiation) {
        json(Json { prettyPrint = true; ignoreUnknownKeys = true })
      }

      install(DefaultRequest) {
        header(HttpHeaders.ContentType, ContentType.Application.Json)
      }

      defaultRequest {
        url("https://www.rijksmuseum.nl/")
        url {
          parameters.append("key", "LI6D4NbA")
        }
      }
    }
  }

  single<MuseumApi> {
    MuseumApi(httpClient = get())
  }
}

