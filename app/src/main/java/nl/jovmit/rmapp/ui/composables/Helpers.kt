package nl.jovmit.rmapp.ui.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

@Composable
fun Int.toDp(): Dp {
  return with(LocalDensity.current) {
    this@toDp.toDp()
  }
}

@Composable
fun Dp.toPx(): Float {
  return with(LocalDensity.current) {
    this@toPx.toPx()
  }
}