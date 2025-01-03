package ie.setu.placemark.ui.screens.run

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ie.setu.placemark.data.model.RunModel
import ie.setu.placemark.data.model.fakeRuns
import ie.setu.placemark.ui.components.run.AmountPicker
import ie.setu.placemark.ui.components.run.MessageInput
import ie.setu.placemark.ui.components.run.ProgressBar
import ie.setu.placemark.ui.components.run.RadioButtonGroup
import ie.setu.placemark.ui.components.run.RunButton
import ie.setu.placemark.ui.components.run.WelcomeText
import ie.setu.placemark.ui.theme.RunHunTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.hilt.navigation.compose.hiltViewModel
import ie.setu.placemark.ui.screens.report.ReportViewModel

@Composable
fun RunScreen(modifier: Modifier = Modifier,
              reportViewModel: ReportViewModel = hiltViewModel(),
              runViewModel: RunViewModel = hiltViewModel()
)
{
    LaunchedEffect(Unit) {
        runViewModel.refreshUnitType()
    }

    val unitType by runViewModel.unitType.collectAsState()
    var distanceAmount by remember { mutableIntStateOf(10) }
    var runMessage by remember { mutableStateOf("Go Hun!") }
    var totalDistance by remember { mutableIntStateOf(0) }

    val runs = reportViewModel.uiRuns.collectAsState().value

    totalDistance = runs.sumOf { it.distanceAmount }

    Column {
        Column(
            modifier = modifier.padding(
                top = 48.dp,
                start = 24.dp,
                end = 24.dp
            ),
            verticalArrangement = Arrangement.spacedBy(30.dp),
        ) {
            WelcomeText()
            Row(
                verticalAlignment = Alignment.CenterVertically,
            )
            {
//                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "How many $unitType did you run?",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.weight(2f)
                )
                AmountPicker(
                    onPaymentAmountChange = { distanceAmount = it },
                    modifier = Modifier.weight(1f)
                )
            }
//            ProgressBar(
//                modifier = modifier,
//                totalDistance = totalDistance)
            MessageInput(
                modifier = modifier,
                onMessageChange = { runMessage = it }
            )
            RunButton (
                modifier = modifier,
                run = RunModel(unitType = unitType,
                    distanceAmount = distanceAmount,
                    message = runMessage),
//                runs = runs,
//                onTotalDistanceChange = { totalDistance = it }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RunScreenPreview() {
    RunHunTheme {
        PreviewRunScreen( modifier = Modifier,
            runs = fakeRuns.toMutableStateList())
    }
}

@Composable
fun PreviewRunScreen(modifier: Modifier = Modifier,
                        runs: SnapshotStateList<RunModel>
) {
    var unitType by remember { mutableStateOf("kilometres") }
    var distanceAmount by remember { mutableIntStateOf(10) }
    var runMessage by remember { mutableStateOf("Go Hun!") }
    var totalDistance by remember { mutableIntStateOf(0) }

    totalDistance = runs.sumOf { it.distanceAmount }

    Column {
        Column(
            modifier = modifier.padding(
                start = 24.dp,
                end = 24.dp
            ),
            verticalArrangement = Arrangement.spacedBy(30.dp),
        ) {
            WelcomeText()
            Row(
                verticalAlignment = Alignment.CenterVertically,
            )
            {
                RadioButtonGroup(
                    modifier = modifier,
                    onPaymentTypeChange = { unitType = it }
                )
                Spacer(modifier.weight(1f))
                AmountPicker(
                    onPaymentAmountChange = { distanceAmount = it }
                )
            }
//            ProgressBar(
//                modifier = modifier,
//                totalDistance = totalDistance)
            MessageInput(
                modifier = modifier,
                onMessageChange = { runMessage = it }
            )
            RunButton (
                modifier = modifier,
                run = RunModel(unitType = unitType,
                    distanceAmount = distanceAmount,
                    message = runMessage),
//                onTotalDistanceChange = { totalDistance = it }
            )
        }
    }
}
