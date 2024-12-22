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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import ie.setu.placemark.R
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.toMutableStateList
import ie.setu.placemark.data.fakeRuns
import ie.setu.placemark.ui.theme.RunHunTheme
import timber.log.Timber
import androidx.compose.ui.platform.LocalContext

@Composable
fun RunButton(
    modifier: Modifier = Modifier,
    run: RunModel,
    runs: SnapshotStateList<RunModel>,
    onTotalDistanceChange: (Int) -> Unit
) {
    var totalDistance by remember { mutableIntStateOf(0) }
    val context = LocalContext.current
    val message = stringResource(R.string.limitExceeded,run.distanceAmount)

    Row {
        Button(
            onClick = {
            if(totalDistance + run.distanceAmount <= 400) {
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
}

@Preview(showBackground = true)
@Composable
fun DonateButtonPreview() {
    RunHunTheme {
        RunButton(
            Modifier,
            RunModel(),
            runs = fakeRuns.toMutableStateList()
        ) {}
    }
}
