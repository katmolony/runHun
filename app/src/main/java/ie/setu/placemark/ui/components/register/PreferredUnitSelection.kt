package ie.setu.placemark.ui.components.register


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PreferredUnitSelection(
    currentUnit: String,
    onUnitSelected: (String) -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .weight(1f)
                .border(BorderStroke(1.dp, Color.Gray))
                .padding(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                RadioButton(
                    selected = currentUnit == "kilometres",
                    onClick = { onUnitSelected("kilometres") }
                )
                Text(
                    text = "Kilometers",
                    modifier = Modifier.clickable { onUnitSelected("km") },
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Box(
            modifier = Modifier
                .weight(1f)
                .border(BorderStroke(1.dp, Color.Gray))
                .padding(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                RadioButton(
                    selected = currentUnit == "miles",
                    onClick = { onUnitSelected("miles") }
                )
                Text(
                    text = "Miles",
                    modifier = Modifier.clickable { onUnitSelected("miles") },
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}