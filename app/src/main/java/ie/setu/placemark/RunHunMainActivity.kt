package ie.setu.placemark

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import ie.setu.placemark.ui.screens.home.HomeScreen
import ie.setu.placemark.ui.theme.RunHunTheme

@AndroidEntryPoint
class RunHunMainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RunHunTheme { HomeScreen() }
        }
    }
}
