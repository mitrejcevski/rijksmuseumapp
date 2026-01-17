package nl.jovmit.rmapp.nav3

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.retain.retain
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import nl.jovmit.rmapp.R
import nl.jovmit.rmapp.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
  selectedTab: NavKey? = null,
  childDestination: NavKey? = null,
  openHomeItemDetails: (itemId: String) -> Unit
) {

  val bottomBarItems = persistentListOf(
    BottomBarItem("Home", painterResource(R.drawable.ic_home), MainDestination.Home),
    BottomBarItem("Profile", painterResource(R.drawable.ic_profile), MainDestination.Profile),
    BottomBarItem("Settings", painterResource(R.drawable.ic_settings), MainDestination.Settings),
  )

  //TODO make the correct initial tab selection based on the [selectedTab] parameter.
  var selectedItem by retain { mutableStateOf(bottomBarItems.first()) }
  val mainScreenBackStack = rememberNavBackStack(selectedItem.destination)
  childDestination?.let { mainScreenBackStack.add(it) }

  Scaffold(
    contentWindowInsets = WindowInsets(),
    bottomBar = {
      MainBottomBar(
        items = bottomBarItems,
        selectedItem = selectedItem,
        onItemSelected = {
          selectedItem = it
          mainScreenBackStack[0] = it.destination
        }
      )
    }
  ) { paddingValues ->
    NavDisplay(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues),
      backStack = mainScreenBackStack,
      entryProvider = entryProvider {
        entry<MainDestination.Home> {
          Screen(
            label = "Home",
            color = Color.Green.copy(.5f),
            onNavigate = { openHomeItemDetails("itemIdHere") }
          )
        }
        entry<MainDestination.Profile> {
          Screen(
            label = "Profile",
            color = Color.Yellow.copy(.5f),
            onNavigate = {
              mainScreenBackStack.add(UserDetails("someUserId"))
            }
          )
        }
        entry<MainDestination.Settings> {
          Screen(
            label = "Settings",
            color = Color.LightGray.copy(.5f),
            onNavigate = {}
          )
        }
        entry<UserDetails> {
          Screen(
            label = "User Details For ${it.userId}",
            color = Color.Magenta,
            onNavigate = {},
            onBack = { mainScreenBackStack.removeLastOrNull() }
          )
        }
      }
    )
  }
}

@Stable
data class BottomBarItem(
  val label: String,
  val icon: Painter,
  val destination: MainDestination
)

@Composable
private fun MainBottomBar(
  items: ImmutableList<BottomBarItem>,
  selectedItem: BottomBarItem,
  onItemSelected: (selected: BottomBarItem) -> Unit
) {
  NavigationBar {
    items.forEach { item ->
      NavigationBarItem(
        icon = {
          Icon(
            painter = item.icon,
            contentDescription = item.label
          )
        },
        selected = selectedItem == item,
        onClick = { onItemSelected(item) }
      )
    }
  }
}

@Preview
@Composable
private fun PreviewMainScreen() {
  AppTheme {
    MainScreen(
      openHomeItemDetails = {}
    )
  }
}