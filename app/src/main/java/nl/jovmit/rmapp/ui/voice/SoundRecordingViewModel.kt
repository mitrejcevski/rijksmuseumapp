package nl.jovmit.rmapp.ui.voice

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.io.File
import java.io.IOException

class SoundRecordingViewModel : ViewModel() {

    private var mediaRecorder: MediaRecorder? = null
    private var audioFile: File? = null

    var isRecording by mutableStateOf(false)
        private set

    var hasRecordingStarted by mutableStateOf(false)
        private set

    fun startRecording(context: Context) {
        if (isRecording) return
        // Define where to store the audio file
        // Using app-specific cache directory for simplicity here.
        // For persistent storage, consider getExternalFilesDir() or other appropriate locations.
        val outputFile = File(context.cacheDir, "audio_recording_${System.currentTimeMillis()}.3gp")
        audioFile = outputFile
        mediaRecorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            MediaRecorder(context)
        } else {
            @Suppress("DEPRECATION")
            MediaRecorder()
        }

        mediaRecorder?.apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            setOutputFile(audioFile?.absolutePath)
            try {
                prepare()
                start()
                isRecording = true
                hasRecordingStarted = true
            } catch (e: IOException) {
                // Handle prepare/start error
                isRecording = false
                hasRecordingStarted = false
                // You might want to show an error message to the user
            }
        }
    }

    fun stopRecording() {
        if (!isRecording) return

        mediaRecorder?.apply {
            try {
                stop()
                release()
            } catch (e: RuntimeException) {
                // Handle stop error (e.g., if stop() is called too soon after start())
                // You might need to delete the outputFile if it's corrupted
            }
        }
        mediaRecorder = null
        isRecording = false
    }

    fun getAudioFilePath(): String? {
        return audioFile?.absolutePath
    }

    override fun onCleared() {
        super.onCleared()
        // Ensure the recorder is released if the ViewModel is cleared while recording
        stopRecording()
    }
}
