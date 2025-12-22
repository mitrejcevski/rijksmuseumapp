package nl.jovmit.rmapp.domain

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import nl.jovmit.rmapp.domain.ArtWork.ArtWorkLinks
import nl.jovmit.rmapp.domain.ArtWork.Image

class ArtWorkBuilder {

  private var links: ArtWorkLinks = ArtWorkLinks("", "", "")
  private var id: String = ""
  private var objectNumber: String = ""
  private var title: String = ""
  private var longTitle: String = ""
  private var headerImage: Image? = null
  private var webImage: Image? = null
  private var productionPlaces: ImmutableList<String> = persistentListOf()

  fun withLinks(links: ArtWorkLinks) = apply {
    this.links = links
  }

  fun withId(id: String) = apply {
    this.id = id
  }

  fun withObjectNumber(objectNumber: String) = apply {
    this.objectNumber = objectNumber
  }

  fun withTitle(title: String) = apply {
    this.title = title
  }

  fun withLongTitle(longTitle: String) = apply {
    this.longTitle = longTitle
  }

  fun withHeaderImage(headerImage: Image) = apply {
    this.headerImage = headerImage
  }

  fun withWebImage(webImage: Image) = apply {
    this.webImage = webImage
  }

  fun withProductionPlaces(vararg productionPlace: String) = apply {
    this.productionPlaces = productionPlace.toImmutableList()
  }

  fun build(): ArtWork {
    return ArtWork(
      links,
      id,
      objectNumber,
      title,
      longTitle,
      headerImage,
      webImage,
      productionPlaces
    )
  }

  companion object {
    fun anArtWork() = ArtWorkBuilder()
  }
}