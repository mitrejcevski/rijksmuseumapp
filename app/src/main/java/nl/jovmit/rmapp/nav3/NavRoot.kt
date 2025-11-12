package nl.jovmit.rmapp.nav3

import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import nl.jovmit.rmapp.R
import nl.jovmit.rmapp.ui.theme.RMAppTheme

@Composable
fun NavRoot() {
  val backStack = rememberSaveable { mutableStateListOf<Any>(WelcomeDestination) }
  NavDisplay(
    modifier = Modifier.fillMaxSize(),
    backStack = backStack,
    onBack = { backStack.removeLastOrNull() },
    entryProvider = entryProvider {
      entry<WelcomeDestination> {
        Screen(
          label = "Welcome",
          color = Color.Blue.copy(.3f),
          onNavigate = {
            backStack.add(MainDestination.MainRoot)
            backStack.removeFirstOrNull()
          }
        )
      }
      entry<MainDestination.MainRoot> {
        MainScreen(
          openHomeItemDetails = {
            backStack.add(HomeItemDetails(it))
          }
        )
      }
      entry<HomeItemDetails> {
        Screen(
          label = "Home Item Details ${it.itemId}",
          color = Color.Red,
          onNavigate = {},
          onBack = { backStack.removeLastOrNull() })
      }
    },
    transitionSpec = {
      slideInHorizontally(initialOffsetX = { it }) togetherWith
        scaleOut(targetScale = .9f)
    },
    popTransitionSpec = {
      scaleIn(initialScale = .9f) togetherWith
        slideOutHorizontally(targetOffsetX = { it })
    },
    predictivePopTransitionSpec = {
      scaleIn(initialScale = .9f) togetherWith
        slideOutHorizontally(targetOffsetX = { it })
    }
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
  openHomeItemDetails: (itemId: String) -> Unit
) {

  val bottomBarItems = persistentListOf(
    BottomBarItem("Home", painterResource(R.drawable.ic_home), MainDestination.Home),
    BottomBarItem("Profile", painterResource(R.drawable.ic_profile), MainDestination.Profile),
    BottomBarItem("Settings", painterResource(R.drawable.ic_settings), MainDestination.Settings),
  )

  var selectedItem by remember { mutableStateOf(bottomBarItems.first()) }
  val mainScreenBackStack = rememberSaveable { mutableStateListOf<Any>(selectedItem.destination) }

  Scaffold(
    contentWindowInsets = WindowInsets(),
    bottomBar = {
      MainBottomBar(
        items = bottomBarItems,
        selectedItem = selectedItem,
        onItemSelected = {
          selectedItem = it
          mainScreenBackStack.add(it.destination)
        }
      )
    }
  ) { paddingValues ->
    NavDisplay(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues),
      backStack = mainScreenBackStack,
      onBack = { mainScreenBackStack.removeLastOrNull() },
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
            onNavigate = {}
          )
        }
        entry<MainDestination.Settings> {
          Screen(
            label = "Settings",
            color = Color.LightGray.copy(.5f),
            onNavigate = {}
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Screen(
  label: String,
  color: Color = Color.Unspecified,
  onNavigate: () -> Unit,
  onBack: (() -> Unit)? = null
) {
  Scaffold(
    containerColor = color,
    topBar = {
      CenterAlignedTopAppBar(
        title = {
          Text(text = label)
        },
        navigationIcon = {
          onBack?.let {
            IconButton(onClick = onBack) {
              Icon(painterResource(R.drawable.ic_arrow_left), "Navigate Up")
            }
          }
        }
      )
    }
  ) { paddingValues ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues),
      contentAlignment = Alignment.Center
    ) {
      Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Screen: $label")
        Button(onClick = onNavigate) {
          Text(text = "Go")
        }
      }
    }
  }
}

@Preview
@Composable
private fun PreviewMainScreen() {
  RMAppTheme {
    MainScreen(
      openHomeItemDetails = {}
    )
  }
}

data object WelcomeDestination

sealed class MainDestination {
  data object MainRoot : MainDestination()
  data object Home : MainDestination()
  data object Profile : MainDestination()
  data object Settings : MainDestination()
}

data class HomeItemDetails(
  val itemId: String
)
