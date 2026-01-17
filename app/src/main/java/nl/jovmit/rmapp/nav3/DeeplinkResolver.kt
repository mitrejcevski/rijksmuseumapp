package nl.jovmit.rmapp.nav3

import androidx.navigation3.runtime.NavKey

class DeeplinkResolver(
  private val fallbackDestination: NavKey,
  private val matchers: List<DeeplinkMatcher> = listOf(
    ArticleDetailsMatcher,
    UserDetailsMatcher
  )
) {

  fun resolve(deeplink: String): NavKey {
    for (matcher in matchers) {
      matcher.match(deeplink)?.let { return it }
    }
    return fallbackDestination
  }
}