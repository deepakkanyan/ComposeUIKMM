package org.white.green

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.FirebaseOptions
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.firestore
import dev.gitlive.firebase.initialize

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "KotlinProject",
    ) {
        val options = FirebaseOptions(
            applicationId = "your-app-id",
            apiKey = "your-api-key",
            databaseUrl = "https://your-project.firebaseio.com",
            projectId = "your-project-id",
            storageBucket = "your-project.appspot.com"
        )

       // init(options) // ✅ Required for desktop

//        val firestore: FirebaseFirestore = Firebase.firestore // ✅ Correct access

        // You can now use Firestore
        App()
    }
}
