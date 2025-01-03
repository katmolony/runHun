package ie.setu.placemark.ui.components.run

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.chargemap.compose.numberpicker.ListItemPicker
import ie.setu.placemark.ui.theme.RunHunTheme

@Composable
fun AmountPicker(
    modifier: Modifier = Modifier,
    onPaymentAmountChange: (Int) -> Unit
) {
    val possibleValues = listOf("1", "3", "5", "10", "15", "24", "44")
    var pickerValue by remember { mutableStateOf(possibleValues[0]) }

    ListItemPicker(
        label = { it },
        dividersColor = MaterialTheme.colorScheme.primary,
        textStyle = TextStyle(Color.Black, 20.sp),
        value = pickerValue,
        onValueChange = {
            pickerValue = it
            onPaymentAmountChange(pickerValue.toInt())
        },
        list = possibleValues,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun PickerPreview() {
    RunHunTheme {
        AmountPicker(modifier = Modifier, onPaymentAmountChange = {})
    }
}