package nl.jovmit.rmapp.nav3

import androidx.navigation3.runtime.NavKey

object UserDetailsMatcher : DeeplinkMatcher {
  val userDetailsRegex = """https://nl.jovmit/user/(.*)""".toRegex()

  override fun match(deeplink: String): NavKey? {
    val userMatch = userDetailsRegex.find(deeplink)
    return userMatch?.let { match ->
      UserDetails(match.groupValues[1])
    }
  }
}