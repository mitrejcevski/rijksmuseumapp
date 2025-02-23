package nl.jovmit.rmapp.domain

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
  private var productionPlaces: List<String> = emptyList()

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
    this.productionPlaces = productionPlace.toList()
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