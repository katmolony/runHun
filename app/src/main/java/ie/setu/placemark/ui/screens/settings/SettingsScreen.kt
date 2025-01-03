package ie.setu.placemark.ui.screens.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale.Companion.Crop
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ie.setu.placemark.R
import ie.setu.placemark.ui.components.general.ClickableCard
import ie.setu.placemark.ui.components.general.HeadingTextComponent

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    val isDarkTheme = settingsViewModel.isDarkTheme.collectAsState().value
    val unitType = settingsViewModel.unitType.collectAsState().value

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        HeadingTextComponent(value = stringResource(id = R.string.settings_title))
        Spacer(
            modifier = Modifier.height(10.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.ic_settings),
            contentDescription = null,
            contentScale = Crop,
            modifier = Modifier.clip(CircleShape).width(180.dp).height(180.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))

        ClickableCard(
            text = if (isDarkTheme) "Switch to Light Mode" else "Switch to Dark Mode",
            onClick = { settingsViewModel.toggleTheme() }
        )

        ClickableCard(
            text = if (unitType == "kilometres") "Switch to miles" else "Switch to kilometres",
            onClick = { settingsViewModel.toggleUnitType() }
        )

        Spacer(modifier = Modifier.height(20.dp))
    }
}