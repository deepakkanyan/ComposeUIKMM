package org.white.green

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.FirebaseApp
import org.koin.compose.KoinApplication
import org.white.green.designSystem.ui.duel.DateInfoView
import org.white.green.di.appModule

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         FirebaseApp.initializeApp(this)
        setContent {
            KoinApplication(application = {
                modules(appModule)
            }) { App() }

        }
    }
}

@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
@Composable
fun AppAndroidPreview() {
    DateInfoView("DOB","12 March, 2024", "26 Y")
}
