package nl.jovmit.rmapp.nav3

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

class DeeplinkResolutionTest {

  @Test
  fun returnsDefaultDestination() {
    val defaultDestination = MainDestination.MainRoot
    val deepLinkResolver = DeeplinkResolver(defaultDestination)

    val result = deepLinkResolver.resolve(":irrelevant:")

    assertThat(result).isEqualTo(defaultDestination)
  }

  @Test
  fun returnsHomeItemDetailsDestination() {
    val articleId = "an_article_id"
    val articleDetailsDeeplink = "https://nl.jovmit/article/$articleId"
    val deeplinkResolver = DeeplinkResolver(fallbackDestination = WelcomeDestination)

    val result = deeplinkResolver.resolve(articleDetailsDeeplink)

    assertThat(result).isEqualTo(HomeItemDetails(articleId))
  }

  @Test
  fun returnsUserDetailsDestination() {
    val anaId = "ana_id"
    val userDetailsDeeplink = "https://nl.jovmit/user/$anaId"
    val deeplinkResolver = DeeplinkResolver(fallbackDestination = WelcomeDestination)

    val result = deeplinkResolver.resolve(userDetailsDeeplink)

    assertThat(result).isEqualTo(UserDetails(anaId))
  }
}