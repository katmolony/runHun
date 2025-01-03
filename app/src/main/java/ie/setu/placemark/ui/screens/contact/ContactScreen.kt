package ie.setu.placemark.ui.screens.contact

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ie.setu.placemark.R
import ie.setu.placemark.ui.components.general.Centre
import ie.setu.placemark.ui.theme.RunHunTheme

@Composable
fun ContactScreen(modifier: Modifier = Modifier) {

    Column(
        modifier = modifier.background(MaterialTheme.colorScheme.secondary),
    ) {
        Centre(Modifier
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
            Column(horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally) {
                Text(
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    lineHeight = 34.sp,
                    textAlign = TextAlign.Center,
                    text = stringResource(R.string.about_message)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Contact Information",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Email: " + stringResource(R.string.contact_email),
                    color = Color.White,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Phone: " + stringResource(R.string.contact_phone),
                    color = Color.White,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Address: " + stringResource(R.string.contact_address),
                    color = Color.White,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Other Important Information",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = stringResource(R.string.contact_important_info),
                    color = Color.White,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
