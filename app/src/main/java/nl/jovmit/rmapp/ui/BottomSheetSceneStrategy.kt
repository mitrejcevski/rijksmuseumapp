package nl.jovmit.rmapp.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.scene.OverlayScene
import androidx.navigation3.scene.Scene
import androidx.navigation3.scene.SceneStrategy
import androidx.navigation3.scene.SceneStrategyScope
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
internal class BottomSheetScene<T : Any>(
  override val key: T,
  override val previousEntries: List<NavEntry<T>>,
  override val overlaidEntries: List<NavEntry<T>>,
  private val entry: NavEntry<T>,
) : OverlayScene<T> {

  override val entries: List<NavEntry<T>> = listOf(entry)

  override val content: @Composable (() -> Unit) = {
    entry.Content()
  }
}

@OptIn(ExperimentalMaterial3Api::class)
class BottomSheetSceneStrategy<T: Any> : SceneStrategy<T> {

  override fun SceneStrategyScope<T>.calculateScene(entries: List<NavEntry<T>>): Scene<T>? {
    val lastEntry = entries.lastOrNull()
    return lastEntry?.metadata?.ifEmpty { null }?.let {
      @Suppress("UNCHECKED_CAST")
      BottomSheetScene(
        key = lastEntry.contentKey as T,
        previousEntries = entries.dropLast(1),
        overlaidEntries = entries.dropLast(1),
        entry = lastEntry
      )
    }
  }

  companion object {

    fun bottomSheet(): Map<String, Any> = mapOf(BOTTOM_SHEET_KEY to UUID.randomUUID())

    internal const val BOTTOM_SHEET_KEY = "bottomSheet"
  }
}