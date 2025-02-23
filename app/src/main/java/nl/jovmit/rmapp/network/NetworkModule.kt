package nl.jovmit.rmapp.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit

private val JSON = Json { ignoreUnknownKeys = true }

val networkModule = module {

  single<AuthInterceptor> { AuthInterceptor() }

  single<OkHttpClient> {
    OkHttpClient.Builder()
      .addInterceptor(get<AuthInterceptor>())
      .build()
  }

  single<Retrofit> {
    Retrofit.Builder()
      .baseUrl("https://www.rijksmuseum.nl/")
      .client(get())
      .addConverterFactory(JSON.asConverterFactory("application/json".toMediaType()))
      .build()
  }

  single<MuseumApi> { get<Retrofit>().create(MuseumApi::class.java) }
}

