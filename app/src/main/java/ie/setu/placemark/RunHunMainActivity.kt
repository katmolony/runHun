package ie.setu.placemark.ui.screens.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import dagger.hilt.android.AndroidEntryPoint
import ie.setu.placemark.ui.screens.home.HomeScreen
import ie.setu.placemark.ui.theme.RunHunTheme
import ie.setu.placemark.ui.theme.ThemeManager

@AndroidEntryPoint
class RunHunMainActivity : ComponentActivity() {

    private lateinit var themeManager: ThemeManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        themeManager = ThemeManager(this)

        setContent {
            // Collect the current theme state
            val isDarkTheme = themeManager.isDarkTheme.collectAsState(initial = false).value

            RunHunTheme(darkTheme = isDarkTheme) {
                HomeScreen(
                    onToggleTheme = { newTheme ->
                        // Update the theme based on the provided newTheme value
                        themeManager.setDarkTheme(newTheme)
                    }
                )
            }
        }
    }
}
