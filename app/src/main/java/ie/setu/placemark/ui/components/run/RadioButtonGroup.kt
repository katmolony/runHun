package ie.setu.placemark.ui.components.run

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import ie.setu.placemark.ui.theme.RunHunTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ie.setu.placemark.R

@Composable
fun RadioButtonGroup(modifier: Modifier = Modifier,
                     onPaymentTypeChange: (String) -> Unit) {

    val radioOptions = listOf(
        stringResource(R.string.metre),
        stringResource(R.string.kilometre),
    )
    var unitType by remember { mutableStateOf(radioOptions[0]) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ){
        radioOptions.forEach { paymentText ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = (paymentText == unitType),
                    onClick = {
                        unitType = paymentText
                        onPaymentTypeChange(unitType)
                    }
                )
                Text(
                    text = paymentText,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun RadioButtonPreview() {
    RunHunTheme {
        RadioButtonGroup(
            Modifier,
            onPaymentTypeChange = {}
        )
    }
}
