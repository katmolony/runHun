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
import ie.setu.placemark.ui.components.badges.BadgeCarousel
import java.text.DateFormat

@Composable
fun AchievementsScreen(
    modifier: Modifier = Modifier,
    onClickRunDetails: (String) -> Unit,
    viewModel: AchievementsViewModel = hiltViewModel()
) {
    val userProfile = viewModel.userProfile.value
    val runs = viewModel.uiRuns.collectAsState().value
    val longestRun by viewModel.longestRun.collectAsState()
    val mostRecentRun by viewModel.mostRecentRun.collectAsState()
    val isError = viewModel.iserror.value
    val isLoading = viewModel.isloading.value
    val error = viewModel.error.value

    LaunchedEffect(Unit) {
        viewModel.fetchAchievements()
    }

    Column(modifier = modifier.padding(16.dp)) {
        userProfile?.let {
            Text(
                text = "Your Achievements",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            BadgeCarousel(userProfile = it)
        }

        Spacer(modifier = Modifier.height(16.dp))

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
    }
}
