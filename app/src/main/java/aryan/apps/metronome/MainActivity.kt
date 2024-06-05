package aryan.apps.metronome

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import aryan.apps.metronome.ui.theme.MetronomeTheme
import kotlinx.coroutines.delay
import kotlin.time.Duration

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MetronomeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Metronome(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

fun playBeep(context: Context) {
    val beepSound = MediaPlayer.create(context, R.raw.tick) // Replace with your beep resource
    beepSound.setOnCompletionListener { it.release() }
    beepSound.start()
}

@Composable
fun Metronome(modifier: Modifier = Modifier) {
    var bpm by remember { mutableIntStateOf(60) } // Initial BPM
    val mContext = LocalContext.current
    var isActive = true

    var previousTime by remember { mutableStateOf(System.currentTimeMillis()) }

    LaunchedEffect(bpm) {
        while (isActive) {
            val currentTime = System.currentTimeMillis()
            val elapsedTime = currentTime - previousTime
            if (elapsedTime >= 60000 / bpm) { // Log or print for verification
                println("Interval elapsed: $elapsedTime ms")
                previousTime = currentTime
            }
            delay((60000 / bpm).toLong()) // Adjust delay for testing (optional)
        }
    }

    Column(modifier = modifier) {
        Button(onClick = { isActive = !isActive }) {
            Text("Toggle metronome")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MetronomeTheme {
        Metronome()
    }


}