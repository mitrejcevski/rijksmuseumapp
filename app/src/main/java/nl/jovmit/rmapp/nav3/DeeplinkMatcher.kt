package nl.jovmit.rmapp.nav3

import androidx.navigation3.runtime.NavKey

interface DeeplinkMatcher {
  fun match(deeplink: String): NavKey?
}