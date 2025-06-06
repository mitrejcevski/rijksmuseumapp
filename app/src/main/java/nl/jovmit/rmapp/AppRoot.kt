package nl.jovmit.rmapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Composable
fun AppRoot() {
  val navController = rememberNavController()
  NavHost(
    modifier = Modifier.fillMaxSize(),
    navController = navController,
    startDestination = AuthDestination
  ) {

    composable<AuthDestination> {
      AuthenticationScreen(
        onNavigateToMain = {
          navController.navigate(MainDestination) {
            popUpTo(0) {
              inclusive = true
            }
          }
        }
      )
    }

    composable<MainDestination> {
      MainScreen(
        onLogout = { navController.navigate(AuthDestination) },
        onNavigateToSettings = { navController.navigate(SettingsDestination) }
      )
    }

    composable<SettingsDestination> {
      SettingsScreen(
        onCloseSettings = { navController.navigateUp() }
      )
    }
  }
}

//region Auth

@Serializable
data object AuthDestination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AuthenticationScreen(
  onNavigateToMain: () -> Unit
) {
  Scaffold(
    topBar = {
      CenterAlignedTopAppBar(
        title = {
          Text(text = "Auth")
        }
      )
    }
  ) { contentPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(contentPadding),
      contentAlignment = Alignment.Center
    ) {
      Button(onClick = onNavigateToMain) {
        Text(text = "Authorize")
      }
    }
  }
}

//endregion

//region Main

@Serializable
data object MainDestination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainScreen(
  onLogout: () -> Unit,
  onNavigateToSettings: () -> Unit
) {

  val navController = rememberNavController()
  val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
  val scope = rememberCoroutineScope()

  ModalNavigationDrawer(
    drawerState = drawerState,
    drawerContent = {
      SideMenuContent(
        onLogout = onLogout,
        onSideMenuItemSelected = { item ->
          navController.navigate(HomePageDestination(item)) {
            popUpTo(0)
          }
          scope.launch { drawerState.close() }
        }
      )
    }
  ) {
    Scaffold(
      bottomBar = {
        MainBottomBar(
          hierarchy = navController.currentBackStackEntryAsState().value
            ?.destination?.hierarchy,
          onHomeTabSelected = { navController.navigate(HomePageDestination(1)) },
          onAccountTabSelected = { navController.navigate(AccountPageDestination) }
        )
      }
    ) { contentPadding ->
      NavHost(
        modifier = Modifier.padding(contentPadding),
        navController = navController,
        startDestination = HomePageDestination(1)
      ) {
        composable<HomePageDestination> { backStackEntry ->
          val destination = backStackEntry.toRoute<HomePageDestination>()
          HomePage(destination.id)
        }
        composable<AccountPageDestination> {
          AccountPage(
            onSettingsClick = onNavigateToSettings
          )
        }
      }
    }
  }
}

@Composable
private fun SideMenuContent(
  onLogout: () -> Unit,
  onSideMenuItemSelected: (current: Int) -> Unit
) {
  var currentItem by remember { mutableIntStateOf(1) }
  ModalDrawerSheet {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(text = "Side Menu")
      IconButton(onClick = onLogout) {
        Icon(
          imageVector = Icons.AutoMirrored.Filled.ExitToApp,
          contentDescription = null
        )
      }
    }
    HorizontalDivider()
    (1..10).map {
      NavigationDrawerItem(
        label = {
          Text(text = "Side Menu Item $it")
        },
        selected = currentItem == it,
        onClick = {
          currentItem = it
          onSideMenuItemSelected(it)
        }
      )
    }
  }
}

@Composable
private fun MainBottomBar(
  hierarchy: Sequence<NavDestination>?,
  onHomeTabSelected: () -> Unit,
  onAccountTabSelected: () -> Unit
) {
  NavigationBar {
    NavigationBarItem(
      selected = hierarchy?.any { it.hasRoute(HomePageDestination::class) } == true,
      onClick = onHomeTabSelected,
      icon = {
        Icon(imageVector = Icons.Default.Home, contentDescription = null)
      },
      label = {
        Text("Home")
      }
    )
    NavigationBarItem(
      selected = hierarchy?.any { it.hasRoute(AccountPageDestination::class) } == true,
      onClick = onAccountTabSelected,
      icon = {
        Icon(imageVector = Icons.Default.AccountCircle, contentDescription = null)
      },
      label = {
        Text("Account")
      }
    )
  }
}

//endregion

//region Home Page

@Serializable
data class HomePageDestination(
  val id: Int
)

@Composable
private fun HomePage(id: Int) {
  Box(
    modifier = Modifier.fillMaxSize(),
    contentAlignment = Alignment.Center
  ) {
    Text("Current Home Page: $id")
  }
}

//endregion

//region Account Page

@Serializable
data object AccountPageDestination

@Composable
private fun AccountPage(
  onSettingsClick: () -> Unit
) {
  Column(
    modifier = Modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    Text("Hello From Account")
    Button(onClick = onSettingsClick) {
      Text(text = "Settings")
    }
  }
}

//endregion

//region Settings

@Serializable
data object SettingsDestination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingsScreen(
  onCloseSettings: () -> Unit
) {
  Scaffold(
    topBar = {
      CenterAlignedTopAppBar(
        navigationIcon = {
          IconButton(onClick = onCloseSettings) {
            Icon(imageVector = Icons.Default.Close, contentDescription = null)
          }
        },
        title = {
          Text(text = "Settings")
        }
      )
    }
  ) { contentPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(contentPadding),
      contentAlignment = Alignment.Center
    ) {
      Button(onClick = onCloseSettings) {
        Text(text = "Close Settings")
      }
    }
  }
}

//endregion