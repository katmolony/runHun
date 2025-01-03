package ie.setu.placemark.ui.screens.options

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
                Button(onClick = {
                    navController.navigate(Achievements.route)
                }) {
                    Icon(painter = painterResource(id = R.drawable.ic_achievements), contentDescription = "Achievements Icon")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Achievements")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { navController.navigate("statistics") }) {
                    Icon(painter = painterResource(id = R.drawable.ic_achievements), contentDescription = "Statistics Icon")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Statistics")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { navController.navigate("settings") }) {
                    Icon(painter = painterResource(id = R.drawable.ic_achievements), contentDescription = "Settings Icon")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Settings")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { navController.navigate("contact") }) {
                    Icon(painter = painterResource(id = R.drawable.ic_achievements), contentDescription = "Contact Icon")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Contact")
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun OptionsScreenPreview() {
//    RunHunTheme {
//        OptionsScreen(modifier = Modifier)
//    }
//}