package ie.setu.placemark.ui.screens.achievements

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ie.setu.placemark.R
import ie.setu.placemark.ui.components.general.Centre
import ie.setu.placemark.ui.components.general.ShowError
import ie.setu.placemark.ui.components.general.ShowLoader
import ie.setu.placemark.ui.components.achievements.AchievementsCard
import ie.setu.placemark.ui.components.achievements.RunGraph
import java.text.DateFormat

@Composable
fun AchievementsScreen(
    modifier: Modifier = Modifier,
    onClickRunDetails: (String) -> Unit,
    viewModel: AchievementsViewModel = hiltViewModel()
) {
    val runs = viewModel.uiRuns.collectAsState().value
//    val mostRecentRun = viewModel.mostRecentRun.collectAsState().value
//    val longestRun = viewModel.longestRun.collectAsState().value

    val longestRun by viewModel.longestRun.collectAsState()
    val mostRecentRun by viewModel.mostRecentRun.collectAsState()

    val isError = viewModel.iserror.value
    val isLoading = viewModel.isloading.value
    val error = viewModel.error.value

    LaunchedEffect(Unit) {
        viewModel.getAchievements()
    }
    Column(modifier = modifier.padding(16.dp)) {
        mostRecentRun?.let { run ->
            Text(
                text = "Most Recent Run",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            AchievementsCard(
                unitType = run.unitType ?: "N/A",
                distanceAmount = run.distanceAmount,
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
                distanceAmount = run.distanceAmount,
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


        if (longestRun == null && mostRecentRun == null) {
            Text(
                text = "No runs found",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
//    Column(
//        modifier = modifier.padding(16.dp)
//    ) {
//        if (isLoading) ShowLoader("Loading Achievements...")
//        if (runs.isEmpty() && !isError)
//            Centre(Modifier.fillMaxSize()) {
//                Text(
//                    color = MaterialTheme.colorScheme.secondary,
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 30.sp,
//                    lineHeight = 34.sp,
//                    textAlign = TextAlign.Center,
//                    text = stringResource(R.string.empty_list)
//                )
//            }
//        if (!isError) {
//            mostRecentRun?.let { run ->
//                Text(
//                    text = "Most Recent Run",
//                    style = MaterialTheme.typography.titleLarge,
//                    modifier = Modifier.padding(bottom = 8.dp)
//                )
//                AchievementsCard(
//                    unitType = run.unitType ?: "N/A",
//                    distanceAmount = run.distanceAmount,
//                    message = run.message ?: "No message",
//                    dateRan = DateFormat.getDateInstance().format(run.dateRan),
//                    onClickRunDetails = { onClickRunDetails(run._id) }
//                )
//            }
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            Text(
//                text = mostRecentRun?.dateRan?.toString() ?: "No recent run found",
//                style = MaterialTheme.typography.titleLarge
//            )
//            Text(
//                text = longestRun?.distanceAmount?.toString() ?: "No longest run found",
//                style = MaterialTheme.typography.titleLarge
//            )
//
//            longestRun?.let { run ->
//                Text(
//                    text = "Longest Distance Run",
//                    style = MaterialTheme.typography.titleLarge,
//                    modifier = Modifier.padding(bottom = 8.dp)
//                )
//                AchievementsCard(
//                    unitType = run.unitType ?: "N/A",
//                    distanceAmount = run.distanceAmount,
//                    message = run.message ?: "No message",
//                    dateRan = DateFormat.getDateInstance().format(run.dateRan),
//                    onClickRunDetails = { onClickRunDetails(run._id) }
//                )
//            }
//
//            Text(
//                text = "Weekly Runs",
//                style = MaterialTheme.typography.titleLarge,
//                modifier = Modifier.padding(bottom = 8.dp)
//            )
//            Column {
//                RunGraph(runs = runs, modifier = Modifier.fillMaxWidth().height(300.dp))
//            }
//        }
//        if (isError) {
//            ShowError(
//                headline = error.message!! + " error...",
//                subtitle = error.toString(),
//                onClick = { viewModel.getAchievements() }
//            )
//        }
//    }
//}