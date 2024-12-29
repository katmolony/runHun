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
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*
import com.github.mikephil.charting.formatter.ValueFormatter

class DayOfWeekValueFormatter(private val daysOfWeek: List<String>) : ValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        val index = value.toInt() % daysOfWeek.size
        return daysOfWeek.getOrNull(index) ?: ""
    }
}

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
            .height(300.dp)
    ) { lineChart ->
        try {
            val entries = runs.mapIndexed { index, run ->
                Entry(index.toFloat(), run.distanceAmount.toFloat())
            }

            if (entries.isNotEmpty()) {
                val dataSet = LineDataSet(entries, "Runs").apply {
                    color = Color.BLUE
                    valueTextColor = Color.BLACK
                }

                val dateFormat = SimpleDateFormat("EEE", Locale.getDefault())
                val daysOfWeek = runs.mapIndexed { index, run ->
                    dateFormat.format(run.dateRan)
                }

                lineChart.data = LineData(dataSet)
                lineChart.xAxis.valueFormatter = DayOfWeekValueFormatter(daysOfWeek)
                lineChart.invalidate()
            } else {
                Log.e("RunGraph", "No valid data entries to display")
            }
        } catch (e: Exception) {
            Log.e("RunGraph", "Error creating chart data", e)
        }
    }
}
