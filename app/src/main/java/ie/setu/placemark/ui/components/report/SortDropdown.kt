package ie.setu.placemark.ui.components.report

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import ie.setu.placemark.ui.screens.report.SortOption

@Composable
fun SortDropdown(
    modifier: Modifier = Modifier,
    currentOption: SortOption,
    onSortOptionSelected: (SortOption) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Row(modifier = modifier) {
        Button(onClick = { expanded = true }) {
            Text(text = "Sort by: ${currentOption.name}")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                onClick = {
                    onSortOptionSelected(SortOption.DATE)
                    expanded = false
                },
                text = { Text("Date") }
            )
            DropdownMenuItem(
                onClick = {
                    onSortOptionSelected(SortOption.DISTANCE)
                    expanded = false
                },
                text = { Text("Distance") }
            )
        }
    }
}
