package ie.setu.placemark.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ie.setu.placemark.R
import ie.setu.placemark.data.RunModel
import ie.setu.placemark.data.fakeRuns
import ie.setu.placemark.ui.components.general.Centre
import ie.setu.placemark.ui.components.report.RunCardList
import ie.setu.placemark.ui.components.report.ReportText
import ie.setu.placemark.ui.theme.RunHunTheme

@Composable
fun ScreenReport(modifier: Modifier = Modifier,
                 runs: SnapshotStateList<RunModel>) {

    Column {
        Column(
            modifier = modifier.padding(
                start = 24.dp,
                end = 24.dp
            ),
        ) {
            ReportText()
            if(runs.isEmpty())
                Centre(androidx.compose.ui.Modifier.fillMaxSize()) {
                    androidx.compose.material3.Text(
                        color = androidx.compose.material3.MaterialTheme.colorScheme.secondary,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                        fontSize = 30.sp,
                        lineHeight = 34.sp,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        text = androidx.compose.ui.res.stringResource(R.string.empty_list)
                    )
                }
            else
                RunCardList(
                    runs = runs
                )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReportScreenPreview() {
    RunHunTheme {
        ScreenReport( modifier = Modifier,
            runs = fakeRuns.toMutableStateList()
        )
    }
}
