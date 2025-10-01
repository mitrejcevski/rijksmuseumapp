package nl.jovmit.rmapp.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CustomLayout(
  modifier: Modifier = Modifier,
  content: @Composable () -> Unit
) {
  Layout(
    modifier = modifier,
    content = content
  ) { measurables, constraints ->
    val height = measurables.maxOf { it.minIntrinsicHeight(constraints.maxWidth) }
    val widths = measurables.map { item -> item.maxIntrinsicWidth(constraints.maxHeight) }
    val totalWidth = widths.sum()

    val itemsToPlace: List<Placeable>
    if (totalWidth > constraints.maxWidth) {
      val itemWidth = constraints.maxWidth / measurables.size
      val itemConstraints = constraints.copy(minWidth = itemWidth, maxWidth = itemWidth)
      itemsToPlace = measurables.map { item -> item.measure(itemConstraints) }
    } else {
      val reminder = (constraints.maxWidth - totalWidth) / measurables.count()
      itemsToPlace = measurables.mapIndexed { index, measurable ->
        val itemWidth = widths[index] + reminder
        measurable.measure(constraints.copy(minWidth = itemWidth, maxWidth = itemWidth))
      }
    }

    layout(constraints.maxWidth, height) {
      var x = 0
      itemsToPlace.forEach { item ->
        item.placeRelative(x, 0)
        x += item.width
      }
    }
  }
}

@Composable
@Preview
private fun PreviewCustomLayout() {
  Column {
    Column(
      modifier = Modifier.width(300.dp).background(Color.White),
      verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
      Column {
        Text(text = "Custom Layout")
        CustomLayout(
          modifier = Modifier.fillMaxWidth(),
        ) {
          Text(
            text = "One More Try",
            textAlign = TextAlign.Center,
            modifier = Modifier.background(Color.Red.copy(.5f))
          )
          Text(
            text = "Two Grow A Little More",
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.background(Color.Green.copy(.5f))
          )
          Text(
            text = "Some Longer Text",
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.background(Color.Red.copy(.5f))
          )
        }
      }

      Column {
        Text(text = "Row")
        Row(
          modifier = Modifier.fillMaxWidth(),
//          horizontalArrangement = Arrangement.SpaceEvenly
        ) {
          Text(
            text = "One",
            textAlign = TextAlign.Center,
            modifier = Modifier
//              .weight(1f)
              .background(Color.Red.copy(.5f))
          )
          Text(
            text = "Two",
            textAlign = TextAlign.Center,
//            maxLines = 1,
//            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
//              .weight(1f)
              .background(Color.Green.copy(.5f))
          )
          Text(
            text = "Some Longer Text That Still Fits",
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
              .weight(1f)
              .background(Color.Red.copy(.5f))
          )
        }
      }
    }
    Spacer(Modifier.height(100.dp))
  }
}