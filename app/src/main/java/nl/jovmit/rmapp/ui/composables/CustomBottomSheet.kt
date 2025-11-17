package nl.jovmit.rmapp.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch
import nl.jovmit.rmapp.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomBottomSheet(
  sheetState: SheetState = rememberModalBottomSheetState(),
  onDismissed: () -> Unit
) {
  val scope = rememberCoroutineScope()

  fun dismiss() = scope.launch {
    sheetState.hide()
  }.invokeOnCompletion {
    onDismissed()
  }

  ModalBottomSheet(
    modifier = Modifier.consumeWindowInsets(
      PaddingValues(bottom = 64.dp)
    ),
    sheetState = sheetState,
    onDismissRequest = { dismiss() }
  ) {
    Box(
      modifier = Modifier.fillMaxHeight(),
      contentAlignment = Alignment.BottomCenter
    ) {

      LazyColumn(
        contentPadding = PaddingValues(bottom = 100.dp)
      ) {
        items(cities) { city ->
          ListItem(
            headlineContent = {
              Text(text = city.name)
            },
            supportingContent = {
              Text(text = city.continent)
            }
          )
        }
      }
      Column(
        modifier = Modifier
          .navigationBarsPadding()
          .offset { IntOffset(x = 0, y = -sheetState.requireOffset().toInt()) }
          .fillMaxWidth()
          .background(Color(0xff2a2d30))
          .padding(12.dp)
          .padding(bottom = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Button(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 100.dp),
          onClick = { dismiss() }
        ) {
          Text(text = "Close")
        }
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
private fun PreviewBottomSheet() {
  AppTheme {
    val sheetState = rememberModalBottomSheetState()
    LaunchedEffect(Unit) {
      sheetState.show()
    }
    CustomBottomSheet(sheetState, {})
  }
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