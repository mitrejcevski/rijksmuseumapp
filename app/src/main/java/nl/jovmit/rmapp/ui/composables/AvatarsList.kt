package nl.jovmit.rmapp.ui.composables

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import nl.jovmit.rmapp.ui.theme.RMAppTheme

@Composable
fun AvatarsList(
  modifier: Modifier = Modifier,
  avatars: List<String>
) {
  val size = 90.dp
  var offset = 0.dp
  var isSpread by remember { mutableStateOf(false) }
  val factor by animateFloatAsState(
    targetValue = if (isSpread) 1f else 2f,
    animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
  )
  Box(
    modifier = modifier
      .clickable { isSpread = !isSpread }
      .graphicsLayer {
        compositingStrategy = CompositingStrategy.Offscreen
      }
  ) {
    for (avatar in avatars) {
      Avatar(
        modifier = Modifier.offset(offset),
        content = {
          AsyncImage(
            modifier = Modifier.size(size),
            model = avatar,
            contentScale = ContentScale.Crop,
            contentDescription = ""
          )
        }
      )
      offset += size / factor
    }
  }
}

@Composable
private fun Avatar(
  modifier: Modifier = Modifier,
  content: @Composable () -> Unit
) {
  Box(
    modifier = modifier
      .drawWithContent {
        drawContent()
        drawCircle(
          color = Color.White,
          style = Stroke(10f),
          blendMode = BlendMode.Clear
        )
      }
      .clip(CircleShape)
  ) {
    content()
  }
}

@Composable
@Preview
private fun PreviewAvatarsList() {
  RMAppTheme {
    Box(
      modifier = Modifier
        .fillMaxSize()
        .background(
          brush = Brush.horizontalGradient(
            colors = listOf(Color(0xFF673AB7), Color(0xFFF44336))
          )
        ),
      contentAlignment = Alignment.Center
    ) {
      AvatarsList(
        modifier = Modifier.fillMaxWidth(),
        avatars = listOf(
          "https://randomuser.me/api/portraits/thumb/men/10.jpg",
          "https://randomuser.me/api/portraits/thumb/men/11.jpg",
          "https://randomuser.me/api/portraits/thumb/men/18.jpg",
          "https://randomuser.me/api/portraits/thumb/men/20.jpg",
          "https://randomuser.me/api/portraits/thumb/men/21.jpg",
          "https://randomuser.me/api/portraits/thumb/men/44.jpg",
        )
      )
    }
  }
}