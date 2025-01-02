package ie.setu.placemark.ui.screens.report

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import ie.setu.placemark.ui.components.profile.ProfileCard
import ie.setu.placemark.ui.components.profile.UserProfile
import ie.setu.placemark.ui.components.report.RunCardList
import ie.setu.placemark.ui.components.report.ReportText
import ie.setu.placemark.ui.theme.RunHunTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import ie.setu.placemark.ui.components.report.SortDropdown

@Composable
fun ReportScreen(
    modifier: Modifier = Modifier,
    onClickRunDetails: (String) -> Unit,
    reportViewModel: ReportViewModel = hiltViewModel()
) {
    val runs = reportViewModel.uiRuns.collectAsState().value
    val sortOption = reportViewModel.sortOption.value

    val userProfile = reportViewModel.userProfile.value

    val isError = reportViewModel.iserror.value
    val isLoading = reportViewModel.isloading.value
    val error = reportViewModel.error.value

    Column(modifier = modifier.padding(24.dp)) {
        Spacer(modifier = Modifier.height(10.dp))

        // Profile Card
        if (userProfile != null) {
            ProfileCard(
                profile = UserProfile(
                    totalDistanceRun = userProfile.totalDistanceRun ?: 0.0,
                    totalRuns = userProfile.totalRuns ?: 0,
                    averagePace = userProfile.averagePace ?: 0.0,
                    preferredUnit = userProfile.preferredUnit ?: "km"
                ),
                onClickEdit = {}
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Sorting Dropdown
        SortDropdown(
            currentOption = sortOption,
            onSortOptionSelected = { reportViewModel.setSortOption(it) }
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Runs or Error State
        if (isLoading) {
            ShowLoader("Loading Runs...")
        } else if (isError) {
            ShowError(
                headline = error.message!! + " error...",
                subtitle = error.toString(),
                onClick = { reportViewModel.getRuns() }
            )
        } else {
            if (runs.isEmpty()) {
                Centre(Modifier.fillMaxSize()) {
                    Text(
                        color = MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        lineHeight = 34.sp,
                        textAlign = TextAlign.Center,
                        text = stringResource(R.string.empty_list)
                    )
                }
            } else {
                RunCardList(
                    runs = runs,
                    onClickRunDetails = onClickRunDetails,
                    onDeleteRun = { run -> reportViewModel.deleteRun(run) }
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ReportScreenPreview() {
    RunHunTheme {
        PreviewReportScreen( modifier = Modifier,
            runs = fakeRuns.toMutableStateList()
        )
    }
}

@Composable
fun PreviewReportScreen(modifier: Modifier = Modifier,
                        runs: SnapshotStateList<RunModel>
) {

    Column {
        Column(
            modifier = modifier.padding(
                start = 24.dp,
                end = 24.dp
            ),
        ) {
            ReportText()
            if(runs.isEmpty())
                Centre(Modifier.fillMaxSize()) {
                    Text(color = MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        lineHeight = 34.sp,
                        textAlign = TextAlign.Center,
                        text = stringResource(R.string.empty_list)
                    )
                }
            else
                ReportText()
                RunCardList(
                    runs = runs,
                    onDeleteRun = {},
                    onClickRunDetails = { },
//                    onRefreshList = { }
                )
        }
    }
}

