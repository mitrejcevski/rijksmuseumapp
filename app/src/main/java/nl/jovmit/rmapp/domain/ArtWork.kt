package nl.jovmit.rmapp.domain

import kotlinx.collections.immutable.ImmutableList

data class ArtWork(
  val links: ArtWorkLinks,
  val id: String,
  val objectNumber: String,
  val title: String,
  val longTitle: String,
  val headerImage: Image? = null,
  val webImage: Image? = null,
  val productionPlaces: ImmutableList<String>
) {

  data class ArtWorkLinks(
    val self: String,
    val web: String,
    val search: String
  )

  data class Image(
    val id: String,
    val width: Int,
    val height: Int,
    val url: String
  ) {

    val aspectRatio: Float
      get() = width.toFloat() / height.toFloat()
  }
}