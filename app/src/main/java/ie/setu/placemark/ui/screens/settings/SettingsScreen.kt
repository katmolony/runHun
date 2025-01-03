package ie.setu.placemark.ui.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ie.setu.placemark.ui.theme.ThemeViewModel

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    settingsViewModel: SettingsViewModel = viewModel()
) {
    val isDarkTheme = settingsViewModel.isDarkTheme.collectAsState().value

    Column(modifier = modifier.padding(16.dp)) {
        Text(text = "Settings")

        Button(onClick = { settingsViewModel.toggleTheme() }) {
            Text(text = if (isDarkTheme) "Switch to Light Mode" else "Switch to Dark Mode")
        }

        // Add language change UI here
    }
}