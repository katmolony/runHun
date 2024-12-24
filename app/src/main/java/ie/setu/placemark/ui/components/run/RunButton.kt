package ie.setu.placemark.ui.components.run

import android.widget.Toast
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ie.setu.placemark.data.RunModel
import ie.setu.placemark.ui.theme.RunHunTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import ie.setu.placemark.R
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.toMutableStateList
import ie.setu.placemark.data.fakeRuns
import timber.log.Timber
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import ie.setu.placemark.ui.components.general.ShowLoader
import ie.setu.placemark.ui.screens.report.ReportViewModel
import ie.setu.placemark.ui.screens.run.RunViewModel

@Composable
fun RunButton(
    modifier: Modifier = Modifier,
    run: RunModel,
    runViewModel: RunViewModel = hiltViewModel(),
    reportViewModel: ReportViewModel = hiltViewModel(),
    onTotalDistanceChange: (Int) -> Unit
)
{
    var totalDistance by remember { mutableIntStateOf(0) }
    val context = LocalContext.current
    val message = stringResource(R.string.limitExceeded,run.distanceAmount)

    val runs = reportViewModel.uiRuns.collectAsState().value

    val isError = runViewModel.isErr.value
    val error = runViewModel.error.value
    val isLoading = runViewModel.isLoading.value

    if(isLoading) ShowLoader("Trying to Add a Run...")


    Row {
        Button(
            onClick = {
            if(totalDistance + run.distanceAmount <= 400) {
                totalDistance+=run.distanceAmount
                onTotalDistanceChange(totalDistance)
                runViewModel.insert(run)
                Timber.i("Run info : $run")
                Timber.i("Run List info : ${runs.toList()}")
            }
            else
                Toast.makeText(context,message,
                    Toast.LENGTH_SHORT).show()
    },
            elevation = ButtonDefaults.buttonElevation(20.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Distance")
            Spacer(modifier.width(width = 4.dp))
            Text(
                text = stringResource(R.string.runButton),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White
            )
        }

        Spacer(modifier.weight(1f))
        Text(

            buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                ) {
                    append(stringResource(R.string.total) + " ")
                }


                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.secondary)
                ) {
                    append(totalDistance.toString())
                }
            })
    }
    //Required to refresh our 'totalRan'
    if(isError)
        Toast.makeText(context,"Unable to add Run at this Time...",
            Toast.LENGTH_SHORT).show()
    else
        reportViewModel.getRuns()

}

@Preview(showBackground = true)
@Composable
fun RunButtonPreview() {
    RunHunTheme {
        PreviewRunButton(
            Modifier,
            RunModel(),
            runs = fakeRuns.toMutableStateList()
        ) {}
    }
}

@Composable
fun PreviewRunButton(
    modifier: Modifier = Modifier,
    run: RunModel,
    runs: SnapshotStateList<RunModel>,
    onTotalDistanceChange: (Int) -> Unit
) {

    var totalDistance= runs.sumOf { it.distanceAmount }
    val context = LocalContext.current
    val message = stringResource(R.string.limitExceeded,run.distanceAmount)

    Row {
        Button(
            onClick = {
                if(totalDistance + run.distanceAmount <= 10000) {
                    totalDistance+=run.distanceAmount
                    onTotalDistanceChange(totalDistance)
                    runs.add(run)
                    Timber.i("Run info : $run")
                    Timber.i("Run List info : ${runs.toList()}")
                }
                else
                    Toast.makeText(context,message,
                        Toast.LENGTH_SHORT).show()
            },
            elevation = ButtonDefaults.buttonElevation(20.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Run")
            Spacer(modifier.width(width = 4.dp))
            Text(
                text = stringResource(R.string.runButton),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White
            )
        }
        Spacer(modifier.weight(1f))
        Text(
            buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                ) {
                    append(stringResource(R.string.total) + " €")
                }


                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.secondary)
                ) {
                    append(totalDistance.toString())
                }
            })
    }
}