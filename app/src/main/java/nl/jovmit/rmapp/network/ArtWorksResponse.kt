package nl.jovmit.rmapp.network

import kotlinx.serialization.Serializable

@Serializable
data class ArtWorksResponse(
  val artObjects: List<ArtObject>
)

@Serializable
data class ArtWorkDetailsResponse(
  val artObject: ArtObject
)

@Serializable
data class ArtObject(
  val links: Links,
  val id: String,
  val objectNumber: String,
  val title: String,
  val longTitle: String,
  val headerImage: ImageEntity? = null,
  val webImage: ImageEntity? = null,
  val productionPlaces: List<String>
) {

  @Serializable
  data class Links(
    val self: String? = null,
    val web: String? = null,
    val search: String? = null
  )

  @Serializable
  data class ImageEntity(
    val guid: String,
    val width: Int,
    val height: Int,
    val url: String
  )
}
