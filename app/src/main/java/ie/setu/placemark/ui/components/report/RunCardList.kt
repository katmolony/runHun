package ie.setu.placemark.ui.components.report

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import ie.setu.placemark.data.RunModel
import ie.setu.placemark.data.fakeRuns
import ie.setu.placemark.ui.theme.RunHunTheme
import java.text.DateFormat

@Composable
internal fun RunCardList(
    runs: List<RunModel>,
    modifier: Modifier = Modifier
) {
    LazyColumn {
        items(
            items = runs,
            key = { run -> run.id }
        ) { run ->
            RunCard(
                unitType = run.unitType,
                distanceAmount = run.distanceAmount,
                message = run.message,
                dateRan = DateFormat.getDateTimeInstance().format(run.dateRan),
            )
        }
    }
}

@Preview(showBackground = true,
    wallpaper = Wallpapers.BLUE_DOMINATED_EXAMPLE
)
@Composable
fun DonationCardListPreview() {
    RunHunTheme {
        RunCardList(fakeRuns.toMutableStateList())
    }
}
