package nl.jovmit.rmapp.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import nl.jovmit.rmapp.ui.theme.AppTheme

@Composable
fun ToolbarTitle(
  modifier: Modifier = Modifier,
  value: String
) {
  Text(
    modifier = modifier,
    text = value,
    maxLines = 1,
    overflow = TextOverflow.Ellipsis,
    color = MaterialTheme.colorScheme.onBackground,
  )
}

@Composable
fun ErrorTitle(
  modifier: Modifier = Modifier,
  value: String
) {
  Text(
    modifier = modifier,
    text = value,
    color = MaterialTheme.colorScheme.onBackground,
    style = MaterialTheme.typography.titleLarge
  )
}

@Composable
fun ErrorLabel(
  modifier: Modifier = Modifier,
  value: String
) {
  Text(
    modifier = modifier,
    text = value,
    color = MaterialTheme.colorScheme.onBackground,
    style = MaterialTheme.typography.labelLarge
  )
}

@Composable
fun ArtWorkTitle(
  modifier: Modifier = Modifier,
  value: String
) {
  Text(
    modifier = modifier,
    text = value,
    color = MaterialTheme.colorScheme.onBackground,
    style = MaterialTheme.typography.titleLarge
  )
}

@Composable
fun ListItemLabel(
  modifier: Modifier = Modifier,
  value: String
) {
  Text(
    modifier = modifier,
    text = value,
    color = MaterialTheme.colorScheme.onBackground,
  )
}

@Composable
@PreviewLightDark
@PreviewFontScale
private fun PreviewToolbarTitle() {
  AppTheme {
    Column(
      modifier = Modifier.background(MaterialTheme.colorScheme.background),
      verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      ToolbarTitle(value = "Rijks Museum")
      ErrorTitle(value = "Oops!")
      ErrorLabel(value = "Error")
      ArtWorkTitle(value = "A Long Title")
      ListItemLabel(value = "The Netherlands")
    }
  }
}