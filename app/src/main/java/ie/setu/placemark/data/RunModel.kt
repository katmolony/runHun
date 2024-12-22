package ie.setu.placemark.data

import java.util.Date
import kotlin.random.Random

data class RunModel(val id: Int = Random.nextInt(1, 100000),
                         val unitType: String = "N/A",
                         val distanceAmount: Int = 0,
                         val message: String = "Go Hun!",
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

