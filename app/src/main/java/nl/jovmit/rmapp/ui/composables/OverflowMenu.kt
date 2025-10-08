package nl.jovmit.rmapp.ui.composables

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import nl.jovmit.rmapp.R
import nl.jovmit.rmapp.ui.theme.AppTheme

data class OverflowMenuItemData(
  val label: String,
  @DrawableRes val icon: Int? = null,
)

object OverflowMenuDefaults {

  @Immutable
  data class Colors(
    val menuIconColor: Color,
    val menuContainerColor: Color,
    val menuItemTextColor: Color,
    val menuItemIconColor: Color
  )

  @Composable
  fun colors(
    menuIconColor: Color = Color.Black,
    menuContainerColor: Color = Color.LightGray,
    menuItemTextColor: Color = Color.Black,
    menuItemIconColor: Color = Color.Black
  ): Colors = Colors(
    menuIconColor,
    menuContainerColor,
    menuItemTextColor,
    menuItemIconColor
  )
}

@Composable
fun OverflowMenu(
  modifier: Modifier = Modifier,
  items: ImmutableList<OverflowMenuItemData>,
  icon: Painter = rememberVectorPainter(Icons.Default.MoreVert),
  colors: OverflowMenuDefaults.Colors = OverflowMenuDefaults.colors(),
  contentDescription: String? = null,
  onItemSelected: (OverflowMenuItemData) -> Unit
) {
  var showMenu by remember { mutableStateOf(false) }
  Box {
    IconButton(
      modifier = modifier,
      onClick = { showMenu = !showMenu }
    ) {
      Icon(
        painter = icon,
        contentDescription = contentDescription,
        tint = colors.menuIconColor
      )
    }
    DropdownMenu(
      containerColor = colors.menuContainerColor,
      expanded = showMenu,
      onDismissRequest = { showMenu = false }
    ) {
      items.forEach { item ->
        DropdownMenuItem(
          colors = MenuDefaults.itemColors(
            textColor = colors.menuItemTextColor,
            leadingIconColor = colors.menuItemIconColor
          ),
          onClick = {
            showMenu = false
            onItemSelected(item)
          },
          text = {
            Text(text = item.label)
          },
          leadingIcon = {
            item.icon?.let { icon ->
              Icon(painter = painterResource(icon), null)
            }
          }
        )
      }
    }
  }
}

@Preview
@Composable
private fun PreviewOverflowMenu() {
  AppTheme {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)
        .padding(AppTheme.size.normal),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      var currentSelection by remember { mutableStateOf<OverflowMenuItemData?>(null) }

      Text(text = currentSelection?.label ?: "None")

      OverflowMenu(
        items = persistentListOf(
          OverflowMenuItemData("Item 1", R.drawable.ic_location),
          OverflowMenuItemData("Item 2", R.drawable.ic_error_missing),
          OverflowMenuItemData("Item 3", R.drawable.ic_connection_error),
        ),
        onItemSelected = { selection ->
          currentSelection = selection
        },
        colors = OverflowMenuDefaults.colors(
          menuIconColor = Color.Black,
          menuContainerColor = Color.DarkGray,
          menuItemTextColor = Color.White,
          menuItemIconColor = Color.White
        )
      )
    }
    Spacer(Modifier.height(200.dp))
  }
}