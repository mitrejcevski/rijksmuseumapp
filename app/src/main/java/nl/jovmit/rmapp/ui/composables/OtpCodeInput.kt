package nl.jovmit.rmapp.ui.composables

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.maxLength
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentType
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nl.jovmit.rmapp.ui.theme.AppTheme

@Composable
fun OtpCodeInput(
  modifier: Modifier = Modifier
) {
  val otpState = rememberTextFieldState()
  Column(
    modifier = modifier,
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    Text(text = "Enter OTP Code")
    BasicTextField(
      state = otpState,
      modifier = Modifier.semantics {
        contentType = ContentType.SmsOtpCode
      },
      inputTransformation = InputTransformation.maxLength(6),
      keyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Number
      ),
      lineLimits = TextFieldLineLimits.SingleLine,
      decorator = {
        val otpCode = otpState.text.toString()
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceAround
        ) {
          repeat(6) { index ->
            Digit(
              char = otpCode.getOrElse(index) { ' ' },
              highlight = index == otpState.text.length
            )
          }
        }
      }
    )
    Text(
      modifier = Modifier.align(Alignment.End),
      text = "Resend Code"
    )
  }
}

@Composable
private fun Digit(
  char: Char,
  highlight: Boolean = false
) {
  val borderSize by animateDpAsState(
    targetValue = if (highlight) 2.dp else 1.dp
  )
  val borderColor by animateColorAsState(
    targetValue = if (highlight) Color.Blue else Color.LightGray
  )
  Box(
    modifier = Modifier
      .size(48.dp)
      .border(borderSize, borderColor, RoundedCornerShape(4.dp))
      .background(Color.Yellow, RoundedCornerShape(4.dp))
  ) {
    Text(
      text = char.toString(),
      fontSize = 24.sp,
      modifier = Modifier.align(Alignment.Center)
    )
  }
}

@Composable
@Preview
private fun PreviewOtpCode() {
  AppTheme {
    Box(
      modifier = Modifier
        .fillMaxSize()
        .background(Color.LightGray)
        .padding(horizontal = 16.dp, vertical = 50.dp)
    ) {
      OtpCodeInput(
        modifier = Modifier
          .clip(RoundedCornerShape(8.dp))
          .background(Color.White)
          .padding(8.dp)
      )
    }
  }
}







