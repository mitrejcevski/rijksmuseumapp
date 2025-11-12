package nl.jovmit.rmapp.nav3

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import nl.jovmit.rmapp.R
import nl.jovmit.rmapp.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Screen(
  label: String,
  color: Color = Color.Unspecified,
  onNavigate: () -> Unit,
  onBack: (() -> Unit)? = null
) {
  Scaffold(
    containerColor = color,
  ) { paddingValues ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .consumeWindowInsets(paddingValues)
        .padding(paddingValues),
      contentAlignment = Alignment.Center
    ) {
      onBack?.let {
        IconButton(
          modifier = Modifier
            .align(Alignment.TopStart)
            .padding(AppTheme.size.normal),
          onClick = onBack
        ) {
          Icon(painterResource(R.drawable.ic_arrow_left), "Navigate Up")
        }
      }
      Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Screen: $label")
        Button(onClick = onNavigate) {
          Text(text = "Go")
        }
      }
    }
  }
}