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
            .height(300.dp) // Set the desired height here
    ) { lineChart ->
        try {
            val entries = runs.mapNotNull { run ->
                val dateRan = run.dateRan
                val distanceAmount = run.distanceAmount.toFloat()

                if (distanceAmount >= 0) {
                    Entry(dateRan.time.toFloat(), distanceAmount)
                } else {
                    null
                }
            }
            if (entries.isNotEmpty()) {
                val dataSet = LineDataSet(entries, "Runs").apply {
                    color = Color.BLUE
                    valueTextColor = Color.BLACK
                }
                lineChart.data = LineData(dataSet)

                // Customize the x-axis to show days of the week
                // Set the x-axis labels (days of the week)
                val dateFormat = SimpleDateFormat("EEE", Locale.getDefault()) // Day of week (e.g., Mon, Tue)
                val daysOfWeek = runs.map { run ->
                    dateFormat.format(run.dateRan)
                }.distinct() // Remove duplicates if any

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
