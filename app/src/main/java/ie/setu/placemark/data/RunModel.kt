package ie.setu.placemark.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.Date

@Entity
data class RunModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val _id: String = "N/A",
    @SerializedName("unitType")
    val unitType: String = "N/A",
    @SerializedName("distanceAmount")
    val distanceAmount: Int = 0,
    var message: String = "Go Hun!",
    @SerializedName("dateRan")
    val dateRan: Date = Date()
)

val fakeRuns = List(5) { i ->
    RunModel(id = 12345 + i,
        _id = "65629$i",
        "metre $i",
        i.toInt(),
        "Message $i",
        Date()
    )
}

