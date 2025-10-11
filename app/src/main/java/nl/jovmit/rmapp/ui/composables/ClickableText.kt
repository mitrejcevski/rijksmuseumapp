package nl.jovmit.rmapp.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import nl.jovmit.rmapp.R
import nl.jovmit.rmapp.ui.theme.AppTheme

@Composable
fun ClickableText(
  modifier: Modifier = Modifier,
  fullText: String,
  clickables: ImmutableList<ClickableTextItem> = persistentListOf()
) {
  val text = createClickableText(
    fullText,
    *clickables.toTypedArray()
  )
  Text(modifier = modifier, text = text)
}

@Immutable
data class ClickableTextItem(
  val clickableText: String,
  val color: Color = Color.Unspecified,
  val onClick: () -> Unit
)

private fun createClickableText(
  fullText: String,
  vararg clickables: ClickableTextItem
): AnnotatedString {
  return buildAnnotatedString {
    append(fullText)
    clickables.forEach { clickable ->
      if (fullText.contains(clickable.clickableText)) {
        addLink(
          clickable = LinkAnnotation.Clickable(
            tag = clickable.clickableText,
            styles = TextLinkStyles(style = SpanStyle(color = clickable.color)),
            linkInteractionListener = {
              clickable.onClick()
            }
          ),
          start = fullText.indexOf(clickable.clickableText),
          end = fullText.indexOf(clickable.clickableText) + clickable.clickableText.length
        )
      }
    }
  }
}

@Composable
@Preview
private fun Preview() {
  AppTheme {
    Column(modifier = Modifier.background(Color.White)) {
      val fullText = stringResource(R.string.terms_and_conditions_full_text)
      val terms = stringResource(R.string.terms_and_conditions)
      val privacy = stringResource(R.string.privacy_policy)

      var lastClicked by remember { mutableStateOf("None") }
      
      Text(text = "Last clicked: $lastClicked")
      ClickableText(
        fullText = fullText,
        clickables = persistentListOf(
          ClickableTextItem("agree", Color.Green) { lastClicked = "None" },
          ClickableTextItem(terms, Color.Red) { lastClicked = terms },
          ClickableTextItem(privacy, Color.Blue) { lastClicked = privacy },
        )
      )
    }
  }
}