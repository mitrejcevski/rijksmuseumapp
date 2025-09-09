package nl.jovmit.rmapp.ui.voice

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SoundRecordingScreen(
  viewModel: SoundRecordingViewModel = viewModel()
) {
  val context = LocalContext.current
  var hasAudioPermission by remember {
    mutableStateOf(
      ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.RECORD_AUDIO
      ) == PackageManager.PERMISSION_GRANTED
    )
  }

  val permissionLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.RequestPermission(),
    onResult = { isGranted ->
      hasAudioPermission = isGranted
    }
  )

  LaunchedEffect(Unit) {
    if (!hasAudioPermission) {
      permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
    }
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    if (hasAudioPermission) {
      Text(
        text = if (viewModel.isRecording) "Recording..." else if (viewModel.hasRecordingStarted) "Recording Stopped" else "Press Record to Start",
        style = MaterialTheme.typography.headlineSmall
      )
      Spacer(modifier = Modifier.height(24.dp))

      Button(
        onClick = {
          if (viewModel.isRecording) {
            viewModel.stopRecording()
          } else {
            viewModel.startRecording(context)
          }
        },
        enabled = hasAudioPermission // Enable button only if permission is granted
      ) {
        Text(text = if (viewModel.isRecording) "Stop Recording" else "Start Recording")
      }

      if (!viewModel.isRecording && viewModel.hasRecordingStarted) {
        Spacer(modifier = Modifier.height(16.dp))
        Text("Recording saved to: ${viewModel.getAudioFilePath() ?: "N/A"}")
        // You could add a button here to play the recording or share it.
      }

    } else {
      Text(
        text = "Audio recording permission is required to use this feature. Please grant the permission.",
        style = MaterialTheme.typography.bodyLarge
      )
      Spacer(modifier = Modifier.height(16.dp))
      Button(onClick = {
        permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
      }) {
        Text("Grant Permission")
      }
    }
  }
}
