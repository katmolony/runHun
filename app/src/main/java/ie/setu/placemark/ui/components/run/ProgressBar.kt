package ie.setu.placemark.ui.components.run

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ie.setu.placemark.ui.theme.RunHunTheme
import kotlin.reflect.KProperty
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun ProgressBar(modifier: Modifier = Modifier,
                totalDistance: Int,) {
    var currentProgress by remember { mutableFloatStateOf(0f) }

    currentProgress = totalDistance / 400f

    LinearProgressIndicator(
        progress = { currentProgress as Float },
        modifier = modifier.padding(top = 120.dp,bottom = 24.dp)
            .height(8.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp)),
        color = MaterialTheme.colorScheme.secondary,
        trackColor = ProgressIndicatorDefaults.linearTrackColor,
        strokeCap = ProgressIndicatorDefaults.LinearStrokeCap
    )

}

@Preview(showBackground = true)
@Composable
fun ProgressBarPreview(){
    RunHunTheme {
        ProgressBar(Modifier, totalDistance = 1000)
    }
}
