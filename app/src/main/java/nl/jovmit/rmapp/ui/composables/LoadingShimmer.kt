package nl.jovmit.rmapp.ui.composables

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import nl.jovmit.rmapp.ui.theme.RMAppTheme

@Composable
fun ListLoadingShimmer(
  modifier: Modifier = Modifier,
  listItemsCount: Int,
  colors: List<Color> = listOf(
    MaterialTheme.colorScheme.background,
    MaterialTheme.colorScheme.onBackground.copy(alpha = .3f)
  )
) {
  Column(modifier = modifier) {
    repeat(listItemsCount) {
      ShimmerItem(
        modifier = Modifier
          .height(96.dp)
          .fillMaxWidth(),
        colors = colors
      )
    }
  }
}

@Composable
private fun ShimmerItem(
  modifier: Modifier = Modifier,
  colors: List<Color>
) {
  val infiniteTransition = rememberInfiniteTransition(label = "background")
  val targetOffset = 1000.dp.toPx()
  val offset by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = targetOffset,
    animationSpec = infiniteRepeatable(
      animation = tween(5000, easing = LinearEasing),
      repeatMode = RepeatMode.Restart
    ),
    label = "offset"
  )
  Spacer(
    modifier = modifier
      .blur(20.dp)
      .drawWithCache {
        val brushSize = 400f
        val brush = Brush.linearGradient(
          colors = colors,
          start = Offset(offset, offset),
          end = Offset(offset + brushSize, offset + brushSize),
          tileMode = TileMode.Mirror
        )
        onDrawBehind {
          drawRect(brush)
        }
      }
  )
}

@Composable
@PreviewLightDark
private fun PreviewLoadingShimmer() {
  RMAppTheme {
    ShimmerItem(
      modifier = Modifier
        .fillMaxWidth()
        .height(100.dp),
      colors = listOf(Color.Red, Color.Yellow)
    )
  }
}

@Composable
@PreviewLightDark
private fun PreviewListLoadingShimmer() {
  RMAppTheme {
    ListLoadingShimmer(
      listItemsCount = 3
    )
  }
}