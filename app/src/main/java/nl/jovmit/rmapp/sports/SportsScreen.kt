package nl.jovmit.rmapp.sports

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Preview
@Composable
fun SportsScreen(
  /*here we need to inject a ViewModel and use it to load the things and collect state from
  * but I will keep the things inside the function here to keep the things simple */
) {

  var screenState by remember { mutableStateOf(SportsScreenState()) }

  Scaffold(
    modifier = Modifier.fillMaxSize(),
    containerColor = Color(0xFF1F2023)
  ) { paddingValues ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
    ) {
      SportSelectionRow(
        modifier = Modifier.fillMaxWidth(),
        selectedSport = screenState.selectedItem,
        sportsList = screenState.sports,
        onSportItemSelected = { selection ->
          screenState = screenState.copy(selectedItem = selection)
        }
      )
    }
  }
}

@Composable
private fun SportSelectionRow(
  modifier: Modifier = Modifier,
  selectedSport: SportItem,
  sportsList: ImmutableList<SportItem>,
  onSportItemSelected: (selection: SportItem) -> Unit
) {
  LazyRow(
    modifier = modifier,
    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
    horizontalArrangement = Arrangement.spacedBy(8.dp)
  ) {
    items(sportsList) { sportItem ->
      SportListItem(
        modifier = Modifier.clickable {
          onSportItemSelected(sportItem)
        },
        sportItem = sportItem,
        isSelected = sportItem == selectedSport,
      )
    }
  }
}

@Composable
private fun SportListItem(
  modifier: Modifier = Modifier,
  sportItem: SportItem,
  isSelected: Boolean = false,
) {
  val backgroundColor by animateColorAsState(
    targetValue = if (isSelected) Color(0xFFFFC425) else Color.Transparent,
    label = "background color"
  )
  Box(modifier = modifier) {
    Row(
      modifier = Modifier
        .clip(RoundedCornerShape(12.dp))
        .background(backgroundColor)
        .border(1.dp, Color.LightGray, RoundedCornerShape(12.dp))
        .padding(12.dp),
      horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
      Box(
        modifier = Modifier
          .size(16.dp)
          .clip(CircleShape)
          .background(sportItem.icon)
      )
      AnimatedVisibility(visible = isSelected) {
        Text(
          text = sportItem.title,
          fontWeight = FontWeight.SemiBold
        )
      }
    }
  }
}

data class SportsScreenState(
  val sports: ImmutableList<SportItem> = someSports,
  val selectedItem: SportItem = sports.first()
)

data class SportItem(
  val title: String,
  val icon: Color // <- this should be the icon string coming from backend, but I don't have it so I use color :D
)

private val someSports = persistentListOf(
  SportItem("Football", Color.Green),
  SportItem("Basketball", Color.Red),
  SportItem("Handball", Color.Gray),
  SportItem("Volleyball", Color.Magenta),
  SportItem("Swimming", Color.Blue),
  SportItem("Tennis", Color.Green),
  SportItem("Racing", Color.Yellow),
  SportItem("Skiing", Color.White),
)