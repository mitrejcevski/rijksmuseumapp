package nl.jovmit.rmapp.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nl.jovmit.rmapp.ui.theme.RMAppTheme

@Composable
fun FireCounter(
  modifier: Modifier = Modifier,
  count: Int
) {
  Box(
    modifier = modifier
      .clip(FireShape())
      .background(Color.Green)
      .padding(4.dp),
    contentAlignment = Alignment.Center
  ) {
    BasicText(
      text = "$count",
      style = MaterialTheme.typography.titleLarge,
      maxLines = 1,
      autoSize = TextAutoSize.StepBased(
        minFontSize = 12.sp,
        maxFontSize = 16.sp
      )
    )
  }
}

@Composable
@Preview
private fun PreviewFireCounter() {
  RMAppTheme {
    FireCounter(
      modifier = Modifier
        .size(width = 40.dp, height = 45.dp),
      count = 0
    )
  }
}