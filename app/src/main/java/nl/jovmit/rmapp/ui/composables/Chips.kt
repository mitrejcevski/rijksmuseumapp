package nl.jovmit.rmapp.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import nl.jovmit.rmapp.ui.theme.AppTheme

@Composable
fun ChipsScreen(
  state: ChipsState,
  onFilterUpdated: (newFilter: String) -> Unit
) {
  Scaffold { paddingValues ->
    Column(
      modifier = Modifier
        .padding(paddingValues)
        .fillMaxSize(),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 12.dp)
      ) {
        items(state.filters) { filter ->
          FilterChip(
            selected = filter == state.currentFilter,
            onClick = { onFilterUpdated(filter) },
            label = { Text(text = filter) }
          )
          /*
          InputChip(selected = false, onClick = {}, label = {})
          AssistChip(onClick = {}, label = {})
          SuggestionChip(onClick = {}, label = {})
          */
        }
      }
      LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        items(state.filteredPlaces) { city ->
          ListItem(
            modifier = Modifier.animateItem(),
            headlineContent = {
              Text(text = city.name)
            },
            supportingContent = {
              Text(text = city.continent)
            }
          )
        }
      }
    }
  }
}

@Preview
@Composable
private fun PreviewChipsScreen() {
  AppTheme {
    var state by remember { mutableStateOf(ChipsState()) }
    ChipsScreen(
      state = state,
      onFilterUpdated = { filter ->
        val newFilter = if (state.currentFilter == filter) "" else filter
        state = state.copy(currentFilter = newFilter)
      }
    )
  }
}

data class ChipsState(
  val places: ImmutableList<City> = cities,
  val currentFilter: String = ""
) {

  val filters: ImmutableList<String>
    get() = places.map { it.continent }.distinct().toPersistentList()

  val filteredPlaces: ImmutableList<City>
    get() = places.filter { it.continent.contains(currentFilter) }.toPersistentList()
}


data class City(
  val name: String,
  val continent: String
)

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