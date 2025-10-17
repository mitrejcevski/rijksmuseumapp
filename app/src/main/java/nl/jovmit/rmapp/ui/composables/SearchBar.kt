package nl.jovmit.rmapp.ui.composables

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.Icons.AutoMirrored.Filled
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import nl.jovmit.rmapp.ui.theme.AppTheme

@Composable
fun ScreenWithSearchBar(
  state: ScreenState,
  onSearchQueryUpdate: (newValue: String) -> Unit
) {
  Scaffold(
    containerColor = AppTheme.colorScheme.background,
    topBar = {
      CustomSearchBar(
        state = state,
        onQueryUpdate = onSearchQueryUpdate,
        onCitySelected = { city ->
          println("Selected City: $city")
        }
      )
    }
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
    ) {
      CitiesList(
        modifier = Modifier.fillMaxSize(),
        cities = state.cities,
        onCitySelected = { city ->
          println("Selected City: $city")
        }
      )
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CustomSearchBar(
  state: ScreenState,
  onQueryUpdate: (newValue: String) -> Unit,
  onCitySelected: (city: City) -> Unit,
) {
  var isSearchExpanded by remember { mutableStateOf(false) }
  val searchBarPadding by animateDpAsState(
    targetValue = if (isSearchExpanded) 0.dp else AppTheme.size.normal,
    label = "searchPadding"
  )
  SearchBar(
    modifier = Modifier
      .fillMaxWidth()
      .padding(searchBarPadding),
    shape = AppTheme.shape.circular,
    colors = SearchBarDefaults.colors(
      containerColor = AppTheme.colorScheme.primary.copy(.5f),
      dividerColor = Color.Unspecified
    ),
    inputField = {
      SearchBarDefaults.InputField(
        query = state.searchQuery,
        onQueryChange = onQueryUpdate,
        onSearch = {},
        colors = TextFieldDefaults.colors(
          cursorColor = AppTheme.colorScheme.onBackground,
          focusedTextColor = AppTheme.colorScheme.onBackground,
          unfocusedTextColor = AppTheme.colorScheme.onBackground.copy(.5f),
          focusedPlaceholderColor = AppTheme.colorScheme.onBackground.copy(.5f),
          unfocusedPlaceholderColor = AppTheme.colorScheme.onBackground.copy(.5f)
        ),
        leadingIcon = {
          IconButton(
            onClick = {
              if (isSearchExpanded) {
                onQueryUpdate("")
              }
              isSearchExpanded = !isSearchExpanded
            }
          ) {
            val icon = if (isSearchExpanded) Filled.ArrowBack else Icons.Default.Search
            val contentDescription = if (isSearchExpanded) "Close search" else "Search"
            Icon(
              imageVector = icon,
              contentDescription = contentDescription,
              tint = AppTheme.colorScheme.onBackground
            )
          }
        },
        placeholder = {
          Text(
            text = "Search cities",
            style = AppTheme.typography.paragraph,
          )
        },
        expanded = isSearchExpanded,
        onExpandedChange = { isExpanded -> isSearchExpanded = isExpanded }
      )
    },
    expanded = isSearchExpanded,
    onExpandedChange = { isExpanded -> isSearchExpanded = isExpanded },
    content = {
      Box(modifier = Modifier.fillMaxSize()) {
        CitiesList(
          modifier = Modifier.fillMaxSize(),
          cities = state.filteredCities,
          onCitySelected = onCitySelected
        )
        if (state.filteredCities.isEmpty()) {
          Text(
            modifier = Modifier.align(Alignment.Center),
            text = "No matching cities",
            style = AppTheme.typography.titleLarge
          )
        }
      }
    }
  )
}

@Composable
private fun CitiesList(
  modifier: Modifier = Modifier,
  cities: ImmutableList<City>,
  onCitySelected: (city: City) -> Unit
) {
  LazyColumn(modifier = modifier) {
    items(cities) { city ->
      ListItem(
        modifier = Modifier.clickable {
          onCitySelected(city)
        },
        colors = ListItemDefaults.colors(
          containerColor = AppTheme.colorScheme.background
        ),
        headlineContent = {
          Text(
            text = city.name,
            color = AppTheme.colorScheme.onBackground,
            style = AppTheme.typography.labelLarge
          )
        },
        supportingContent = {
          Text(
            text = city.continent,
            color = AppTheme.colorScheme.onBackground.copy(.7f),
            style = AppTheme.typography.labelNormal
          )
        }
      )
    }
  }
}

data class ScreenState(
  val searchQuery: String = "",
  val cities: ImmutableList<City> = persistentListOf()
) {

  val filteredCities: ImmutableList<City>
    get() = cities
      .filter {
        it.name.contains(searchQuery, ignoreCase = true) ||
          it.continent.contains(searchQuery, ignoreCase = true)
      }
      .toPersistentList()
}

data class City(
  val name: String,
  val continent: String
)

@Preview
@Composable
private fun PreviewScreenWithSearchBar() {
  AppTheme {
    var state by remember { mutableStateOf(ScreenState(cities = cities)) }
    ScreenWithSearchBar(
      state = state,
      onSearchQueryUpdate = { newQuery ->
        state = state.copy(searchQuery = newQuery)
      }
    )
  }
}

private val cities = persistentListOf(
  City(
    name = "New York",
    continent = "North America"
  ),
  City(
    name = "Los Angeles",
    continent = "North America"
  ),
  City(
    name = "Toronto",
    continent = "North America"
  ),
  City(
    name = "Mexico City",
    continent = "North America"
  ),
  City(
    name = "Chicago",
    continent = "North America"
  ),
  City(
    name = "São Paulo",
    continent = "South America"
  ),
  City(
    name = "Buenos Aires",
    continent = "South America"
  ),
  City(
    name = "Rio de Janeiro",
    continent = "South America"
  ),
  City(
    name = "Lima",
    continent = "South America"
  ),
  City(
    name = "Bogotá",
    continent = "South America"
  ),
  City(
    name = "London",
    continent = "Europe"
  ),
  City(
    name = "Paris",
    continent = "Europe"
  ),
  City(
    name = "Berlin",
    continent = "Europe"
  ),
  City(
    name = "Amsterdam",
    continent = "Europe"
  ),
  City(
    name = "Madrid",
    continent = "Europe"
  ),
  City(
    name = "Rome",
    continent = "Europe"
  ),
  City(
    name = "Vienna",
    continent = "Europe"
  ),
  City(
    name = "Prague",
    continent = "Europe"
  ),
  City(
    name = "Warsaw",
    continent = "Europe"
  ),
  City(
    name = "Oslo",
    continent = "Europe"
  ),
  City(
    name = "Cairo",
    continent = "Africa"
  ),
  City(
    name = "Lagos",
    continent = "Africa"
  ),
  City(
    name = "Nairobi",
    continent = "Africa"
  ),
  City(
    name = "Johannesburg",
    continent = "Africa",
  ),
  City(
    name = "Casablanca",
    continent = "Africa"
  ),
  City(
    name = "Accra",
    continent = "Africa"
  ),
  City(
    name = "Tokyo",
    continent = "Asia"
  ),
  City(
    name = "Seoul",
    continent = "Asia"
  ),
  City(
    name = "Beijing",
    continent = "Asia"
  ),
  City(
    name = "Shanghai",
    continent = "Asia"
  ),
  City(
    name = "Bangkok",
    continent = "Asia"
  ),
  City(
    name = "Jakarta",
    continent = "Asia"
  ),
  City(
    name = "Manila",
    continent = "Asia"
  ),
  City(
    name = "Mumbai",
    continent = "Asia"
  ),
  City(
    name = "Delhi",
    continent = "Asia"
  ),
  City(
    name = "Singapore",
    continent = "Asia"
  ),
  City(
    name = "Hong Kong",
    continent = "Asia"
  ),
  City(
    name = "Sydney",
    continent = "Australia"
  ),
  City(
    name = "Melbourne",
    continent = "Australia"
  ),
  City(
    name = "Brisbane",
    continent = "Australia"
  ),
  City(
    name = "Perth",
    continent = "Australia"
  ),
  City(
    name = "Auckland",
    continent = "Australia"
  ),
  City(
    name = "Wellington",
    continent = "Australia"
  ),
  City(
    name = "Honolulu",
    continent = "North America"
  ),
  City(
    name = "Vancouver",
    continent = "North America"
  ),
  City(
    name = "Montreal",
    continent = "North America"
  ),
  City(
    name = "Santiago",
    continent = "South America"
  ),
  City(
    name = "Doha",
    continent = "Asia"
  ),
  City(
    name = "Dubai",
    continent = "Asia"
  ),
)