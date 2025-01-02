package ie.setu.placemark

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ie.setu.placemark.navigation.AppDestination
import ie.setu.placemark.navigation.Home
import ie.setu.placemark.navigation.NavHostProvider
import ie.setu.placemark.ui.theme.RunHunTheme
import ie.setu.placemark.ui.theme.ThemeViewModel

@AndroidEntryPoint
class RunHunMainActivity : ComponentActivity() {
    private val themeViewModel: ThemeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            val isDarkTheme = themeViewModel.isDarkTheme.collectAsState().value
            RunHunTheme(darkTheme = isDarkTheme) {
                NavHostProvider(
                    modifier = Modifier,
                    navController = navController,
                    startDestination = Home,
                    paddingValues = PaddingValues(),
                    themeViewModel = themeViewModel
                )
            }
        }
    }
}