package ie.setu.placemark.ui.screens.options


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ie.setu.placemark.R
import ie.setu.placemark.navigation.Achievements
import ie.setu.placemark.ui.components.general.Centre
import ie.setu.placemark.ui.components.options.OptionCard

@Composable
fun OptionsScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Column(
        modifier = modifier.background(MaterialTheme.colorScheme.secondary),
    ) {
        Centre(
            Modifier
                .fillMaxWidth()
                .padding(top = 48.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.runner),
                contentDescription = "Run Logo",
                modifier = Modifier.size(350.dp)
            )
        }
        Centre(Modifier.fillMaxSize()) {
            Column {
                Text(
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    lineHeight = 34.sp,
                    textAlign = TextAlign.Center,
                    text = stringResource(R.string.options_message)
                )
                Spacer(modifier = Modifier.height(16.dp))
                OptionCard(
                    icon = painterResource(id = R.drawable.ic_achievements),
                    contentDescription = "Achievements Icon",
                    text = "Achievements",
                    onClick = { navController.navigate(Achievements.route) }
                )
//                OptionCard(
//                    icon = painterResource(id = R.drawable.ic_achievements),
//                    contentDescription = "Statistics Icon",
//                    text = "Statistics",
//                    onClick = { navController.navigate("statistics") }
//                )
                OptionCard(
                    icon = painterResource(id = R.drawable.ic_settings),
                    contentDescription = "Settings Icon",
                    text = "Settings",
                    onClick = { navController.navigate("settings") }
                )
                OptionCard(
                    icon = painterResource(id = R.drawable.ic_contact),
                    contentDescription = "Contact Icon",
                    text = "Contact",
                    onClick = { navController.navigate("contact") }
                )
            }
        }
    }
}