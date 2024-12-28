package ie.setu.placemark.ui.screens.achievements

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ie.setu.placemark.R
import ie.setu.placemark.data.model.RunModel
import ie.setu.placemark.data.model.fakeRuns
import ie.setu.placemark.ui.components.general.Centre
import ie.setu.placemark.ui.components.general.ShowError
import ie.setu.placemark.ui.components.general.ShowLoader
import ie.setu.placemark.ui.components.general.ShowRefreshList
import ie.setu.placemark.ui.components.report.RunCardList
import ie.setu.placemark.ui.components.report.ReportText
import ie.setu.placemark.ui.theme.RunHunTheme
import ie.setu.placemark.ui.components.achievements.AchievementsCard
import androidx.compose.foundation.layout.Spacer
import java.text.DateFormat
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import ie.setu.placemark.ui.components.achievements.RunGraph

@Composable
fun AchievementsScreen(
    modifier: Modifier = Modifier,
    onClickRunDetails: (String) -> Unit,
    viewModel: AchievementsViewModel = hiltViewModel()
) {
    val runs = viewModel.uiRuns.collectAsState().value
    val mostRecentRun = viewModel.mostRecentRun.collectAsState().value
    val longestRun = viewModel.longestRun.collectAsState().value

    val isError = viewModel.isError.value
    val isLoading = viewModel.isLoading.value
    val error = viewModel.error.value

    LaunchedEffect(Unit) {
        viewModel.getRuns()
    }

    Column(
        modifier = modifier.padding(16.dp)
    ) {
        if (isLoading) {
            ShowLoader("Loading Achievements...")
        } else if (isError) {
            ShowError(
                headline = "Error occurred",
                subtitle = "$error.message Unknown error",
                onClick = { viewModel.getRuns() }
            )
        } else {
            if (mostRecentRun == null && longestRun == null) {
                Centre(Modifier.fillMaxSize()) {
                    Text(
                        text = stringResource(R.string.empty_list),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.secondary,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    )
                }
            } else {
                mostRecentRun?.let { run ->
                    Text(
                        text = "Most Recent Run",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    AchievementsCard(
                        unitType = run.unitType ?: "N/A",
                        distanceAmount = run.distanceAmount ?: 0,
                        message = run.message ?: "No message",
                        dateRan = DateFormat.getDateInstance().format(run.dateRan),
                        onClickRunDetails = { onClickRunDetails(run._id) }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                longestRun?.let { run ->
                    Text(
                        text = "Longest Distance Run",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    AchievementsCard(
                        unitType = run.unitType ?: "N/A",
                        distanceAmount = run.distanceAmount ?: 0,
                        message = run.message ?: "No message",
                        dateRan = DateFormat.getDateInstance().format(run.dateRan),
                        onClickRunDetails = { onClickRunDetails(run._id) }
                    )
                }
                Text(
                    text = "Weekly Runs",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Column {
                    RunGraph(runs = runs, modifier = Modifier.fillMaxWidth().height(300.dp))
                }
            }
        }
    }
}
