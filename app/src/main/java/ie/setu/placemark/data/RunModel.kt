package ie.setu.placemark.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import kotlin.random.Random

@Entity
data class RunModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val unitType: String = "N/A",
    val distanceAmount: Int = 0,
    var message: String = "Go Hun!",
    val dateRan: Date = Date()
)

val fakeRuns = List(30) { i ->
    RunModel(id = 12345 + i,
        "metre $i",
        i.toInt(),
        "Message $i",
        Date()
    )
}

