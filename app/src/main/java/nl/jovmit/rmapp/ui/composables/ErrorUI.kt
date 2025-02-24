package nl.jovmit.rmapp.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import nl.jovmit.rmapp.R
import nl.jovmit.rmapp.ui.theme.RMAppTheme

@Composable
fun ErrorUI(
  modifier: Modifier = Modifier,
  icon: Painter,
  title: String,
  message: String
) {
  Column(
    modifier = modifier,
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    Icon(
      modifier = Modifier.size(56.dp),
      painter = icon,
      contentDescription = message,
      tint = MaterialTheme.colorScheme.onBackground
    )
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
      ErrorTitle(value = title)
      ErrorLabel(value = message)
    }
  }
}

@Composable
@PreviewLightDark
private fun ErrorPreview() {
  RMAppTheme {
    ErrorUI(
      icon = painterResource(R.drawable.ic_connection_error),
      title = stringResource(R.string.label_error_title),
      message = stringResource(R.string.label_connection_error)
    )
  }
}