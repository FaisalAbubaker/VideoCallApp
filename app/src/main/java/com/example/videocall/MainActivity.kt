package com.example.videocall

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.videocall.ui.theme.VideoCallTheme
import io.getstream.video.android.compose.permission.LaunchCallPermissions
import io.getstream.video.android.compose.theme.VideoTheme
import io.getstream.video.android.compose.ui.components.call.activecall.CallContent
import io.getstream.video.android.compose.ui.components.call.renderer.FloatingParticipantVideo
import io.getstream.video.android.compose.ui.components.call.renderer.ParticipantVideo
import io.getstream.video.android.core.GEO
import io.getstream.video.android.core.RealtimeConnection
import io.getstream.video.android.core.StreamVideoBuilder
import io.getstream.video.android.model.User

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val apiKey = "mmhfdzb5evj2"
        val userToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiRGFydGhfS3JheXQiLCJpc3MiOiJodHRwczovL3Byb250by5nZXRzdHJlYW0uaW8iLCJzdWIiOiJ1c2VyL0RhcnRoX0tyYXl0IiwiaWF0IjoxNzE3NDIzODA2LCJleHAiOjE3MTgwMjg2MTF9.FEDNa6FX-uPl3RcN9FGVs0zzUXe9-SYA2tOGMj3ENJQ"
        val userId = "Darth_Krayt"
        val callId = "V93ui2rhdguP"

        // Create a user.
        val user = User(
            id = userId, // any string
            name = "Tutorial", // name and image are used in the UI
            image = "https://bit.ly/2TIt8NR",
        )

        // Initialize StreamVideo. For a production app, we recommend adding the client to your Application class or di module.
        val client = StreamVideoBuilder(
            context = applicationContext,
            apiKey = apiKey,
            geo = GEO.GlobalEdgeNetwork,
            user = user,
            token = userToken,
        ).build()

        setContent {
            // Request permissions and join a call, which type is `default` and id is `123`.
            val call = client.call(type = "default", id = callId)
            LaunchCallPermissions(
                call = call,
                onAllPermissionsGranted = {
                    // All permissions are granted so that we can join the call.
                    val result = call.join(create = true)
                    result.onError {
                        Toast.makeText(applicationContext, it.message, Toast.LENGTH_LONG).show()
                    }
                }
            )

            // Apply VideTheme
            VideoTheme { // Define required properties.
                CallContent(
                    modifier = Modifier.fillMaxSize(),
                    call = call,
                    onBackPressed = { onBackPressed() },
                )
            }
        }
    }
}