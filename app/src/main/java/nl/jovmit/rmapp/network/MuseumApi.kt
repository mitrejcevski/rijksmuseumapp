package nl.jovmit.rmapp.network

import retrofit2.http.GET
import retrofit2.http.Path

interface MuseumApi {

  @GET("api/en/collection")
  suspend fun getArtWorksList(): ArtWorksResponse

  @GET("api/en/collection/{objectNumber}")
  suspend fun getArtWorkDetails(
    @Path("objectNumber") objectNumber: String
  ): ArtWorkDetailsResponse
}