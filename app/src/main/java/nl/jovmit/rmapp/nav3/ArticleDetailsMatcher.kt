package nl.jovmit.rmapp.nav3

import androidx.navigation3.runtime.NavKey

object ArticleDetailsMatcher : DeeplinkMatcher {
    val articleDetailsRegex = """https://nl.jovmit/article/(.*)""".toRegex()

    override fun match(deeplink: String): NavKey? {
      val articleMatch = articleDetailsRegex.find(deeplink)
      return articleMatch?.let { match ->
        HomeItemDetails(match.groupValues[1])
      }
    }
  }