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

  fun resolveTree(deeplink: String): NavKey {
    return when (val destination = resolve(deeplink)) {
      is HomeItemDetails -> return destination
      is UserDetails -> MainDestination.MainRoot(
        selectedTab = MainDestination.Profile,
        childDestination = destination
      )

      else -> fallbackDestination
    }
  }
}