package nl.jovmit.rmapp.nav3

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object WelcomeDestination : NavKey

@Serializable
sealed class MainDestination : NavKey {

  @Serializable
  data object MainRoot : MainDestination()

  @Serializable
  data object Home : MainDestination()

  @Serializable
  data object Profile : MainDestination()

  @Serializable
  data object Settings : MainDestination()
}

@Serializable
data class HomeItemDetails(
  val itemId: String
) : NavKey

@Serializable
data class UserDetails(
  val userId: String
) : NavKey