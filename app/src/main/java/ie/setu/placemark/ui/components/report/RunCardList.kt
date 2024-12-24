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
    modifier: Modifier = Modifier,
    onDeleteRun: (RunModel) -> Unit,
    onClickRunDetails: (String) -> Unit,
    onRefreshList: () -> Unit
) {
    LazyColumn {
        items(
            items = runs,
            key = { run -> run._id }
        ) { run ->
            RunCard(
                unitType = run.unitType,
                distanceAmount = run.distanceAmount,
                message = run.message,
                dateRan = DateFormat.getDateTimeInstance().format(run.dateRan),
                onClickDelete = { onDeleteRun(run) },
                onClickRunDetails = { onClickRunDetails(run._id) },
                onRefreshList = onRefreshList
            )
        }
    }
}

@Preview(showBackground = true,
    wallpaper = Wallpapers.BLUE_DOMINATED_EXAMPLE
)
@Composable
fun RunCardListPreview() {
    RunHunTheme {
        RunCardList(
            fakeRuns.toMutableStateList(),
            onDeleteRun = {},
            onClickRunDetails = { },
            onRefreshList = { },
        )
    }
}
