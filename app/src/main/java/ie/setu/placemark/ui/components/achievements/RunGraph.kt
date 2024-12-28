package ie.setu.placemark.ui.components.achievements


import android.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import ie.setu.placemark.data.model.RunModel
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RunGraph(runs: List<RunModel>, modifier: Modifier = Modifier) {
    AndroidView(
        factory = { context ->
            LineChart(context).apply {
                xAxis.position = XAxis.XAxisPosition.BOTTOM
                axisRight.isEnabled = false
                description.isEnabled = false
                setTouchEnabled(true)
                setPinchZoom(true)
                setBackgroundColor(Color.WHITE)
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp) // Set the desired height here
    ) { lineChart ->
        val entries = runs.map { run ->
            Entry(run.dateRan.time.toFloat(), run.distanceAmount.toFloat())
        }
        val dataSet = LineDataSet(entries, "Runs").apply {
            color = Color.BLUE
            valueTextColor = Color.BLACK
        }
        lineChart.data = LineData(dataSet)
        lineChart.invalidate()
    }
}