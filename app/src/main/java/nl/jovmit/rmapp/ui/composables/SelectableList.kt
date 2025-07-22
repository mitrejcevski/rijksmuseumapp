package nl.jovmit.rmapp.ui.composables

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nl.jovmit.rmapp.ui.theme.AppTheme
import java.util.UUID

data class DataItem(
  val id: String,
  val title: String,
  val interestRate: Double,
  val monthlyPaymentInCents: Long
) {

  val monthlyPaymentFormatted: String
    get() = String.format("$%.2f", monthlyPaymentInCents/100.0)
}

@Composable
fun SelectableList(
  modifier: Modifier = Modifier,
  items: List<DataItem>,
  selectedItem: DataItem? = null,
  onItemSelected: (choice: DataItem) -> Unit
) {
  LazyColumn(
    modifier = modifier,
    contentPadding = PaddingValues(
      horizontal = AppTheme.size.small,
      vertical = AppTheme.size.normal
    ),
    verticalArrangement = Arrangement.spacedBy(
      AppTheme.size.small
    )
  ) {
    items(
      items = items,
      key = { item -> item.id }
    ) { dataItem ->
      SelectableListItem(
        data = dataItem,
        selected = dataItem == selectedItem,
        onItemSelected = onItemSelected
      )
    }
  }
}

@Composable
private fun SelectableListItem(
  modifier: Modifier = Modifier,
  data: DataItem,
  selected: Boolean,
  onItemSelected: (choice: DataItem) -> Unit
) {
  Column(modifier = modifier) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .clip(AppTheme.shape.container)
        .border(1.dp, Color.Gray, AppTheme.shape.container)
        .clickable { onItemSelected(data) }
        .padding(AppTheme.size.small),
      horizontalArrangement = Arrangement.spacedBy(
        AppTheme.size.small
      )
    ) {
      Check(isChecked = selected)
      Column(
        verticalArrangement = Arrangement.spacedBy(
          AppTheme.size.medium
        )
      ) {
        Text(data.title)
        Row(
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(
              AppTheme.size.small
            )
          ) {
            Text(
              text = "Interest rate",
              style = AppTheme.typography.labelSmall
            )
            Text("${data.interestRate}%")
          }
          Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(
              AppTheme.size.small
            )
          ) {
            Text(
              text = "Monthly Payment",
              style = AppTheme.typography.labelSmall
            )
            Text(
              text = data.monthlyPaymentFormatted
            )
          }
        }
      }
    }
  }
}

@Composable
private fun Check(
  modifier: Modifier = Modifier,
  isChecked: Boolean
) {
  val background by animateColorAsState(
    targetValue = if (isChecked) AppTheme.colorScheme.primary else Color.Transparent,
    animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
  )
  val borderColor by animateColorAsState(
    targetValue = if (isChecked) AppTheme.colorScheme.primary else Color.Gray,
    animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
  )
  Box(
    modifier = modifier
      .size(24.dp)
      .clip(CircleShape)
      .border(1.dp, borderColor, CircleShape)
      .background(background)
  ) {
    if (isChecked) {
      Icon(
        imageVector = Icons.Default.Check,
        contentDescription = null
      )
    }
  }
}

@Preview
@Composable
private fun PreviewCheck() {
  AppTheme {
    var isChecked by remember { mutableStateOf(false) }
    Row(
      modifier = Modifier.clickable {
        isChecked = !isChecked
      },
      horizontalArrangement = Arrangement.spacedBy(AppTheme.size.normal)
    ) {
      Check(isChecked = isChecked)
      Check(isChecked = !isChecked)
    }
  }
}

@Preview
@Composable
private fun PreviewListItem() {
  AppTheme {
    Column(
      modifier = Modifier
        .background(AppTheme.colorScheme.background)
        .padding(AppTheme.size.normal)
    ) {
      SelectableListItem(
        data = DataItem(
          id = UUID.randomUUID().toString(),
          title = "60 Months Varable",
          interestRate = 6.45,
          monthlyPaymentInCents = 77361
        ),
        selected = false,
        onItemSelected = {}
      )
      SelectableListItem(
        data = DataItem(
          id = UUID.randomUUID().toString(),
          title = "1 Year Fixed",
          interestRate = 5.67,
          monthlyPaymentInCents = 70236
        ),
        selected = true,
        onItemSelected = {}
      )
    }
  }
}

@Preview(showBackground = true, device = "spec:width=411dp,height=891dp")
@Composable
private fun PreviewSelectableList() {
  AppTheme {
    val items = listOf(
      DataItem(
        id = "0",
        title = "60 Months Varable",
        interestRate = 6.45,
        monthlyPaymentInCents = 77361
      ),
      DataItem(
        id = "1",
        title = "6 Month Fixed",
        interestRate = 7.99,
        monthlyPaymentInCents = 66356
      ),
      DataItem(
        id = "2",
        title = "1 Year Fixed Closed",
        interestRate = 6.49,
        monthlyPaymentInCents = 60301
      ),
      DataItem(
        id = "3",
        title = "1 Year Fixed Open",
        interestRate = 6.95,
        monthlyPaymentInCents = 62129
      ),
      DataItem(
        id = "4",
        title = "2 Year Fixed Open",
        interestRate = 5.95,
        monthlyPaymentInCents = 31065
      )
    )
    var selectedItem by remember { mutableStateOf<DataItem?>(null) }

    SelectableList(
      modifier = Modifier.fillMaxSize(),
      items = items,
      selectedItem = selectedItem,
      onItemSelected = {
        selectedItem = if (selectedItem == it) null else it
      }
    )
  }
}