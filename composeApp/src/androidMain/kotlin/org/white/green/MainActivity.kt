package org.white.green

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.FirebaseApp
import org.koin.compose.KoinApplication
import org.white.green.di.appModule
import org.white.green.feature.match.MatchList
import org.white.green.feature.match.repo.CompleteProfile
import org.white.green.feature.profile.ui.basicProfile.BasicProfileModel
import org.white.green.feature.profile.ui.family.FamilyInfoModel
import org.white.green.feature.profile.ui.personal.AgeRange
import org.white.green.feature.profile.ui.personal.HeightRange
import org.white.green.feature.profile.ui.personal.PersonalModel

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

@Preview(backgroundColor = 0xFFFFFF, showBackground = true)
@Composable
fun AppAndroidPreview() {
    Box {
        MatchList(profiles = listOf(fakeCompleteProfile))
    }
}

val fakeCompleteProfile = CompleteProfile(
    userProfile = BasicProfileModel(
        name = "Amit Sharma",
        email = "amit.sharma@example.com",
        phone = "+91 98765 43210",
        bio = "A passionate software developer who loves exploring new technologies and building innovative apps."
    ),
    family = FamilyInfoModel(
        fatherName = "Rakesh Sharma",
        fatherOccupation = "Businessman",
        motherName = "Sunita Sharma",
        motherOccupation = "Homemaker",
        sistersCount = 1,
        brothersCount = 2
    ),
    bio = PersonalModel(
        ageRange = AgeRange(dobTimeMiles = 599616000000), // Approx. 1995-01-01
        heightRange = HeightRange(height = 200), // 5'7" (67 inches)
        complexion = "Fair",
        education = "B.Tech in Computer Science",
        gotra = "Kashyap",
        location = "Mumbai",
        state = "Maharashtra",
        country = "India"
    )
)






