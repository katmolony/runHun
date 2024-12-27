package ie.setu.placemark.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.Date
import java.util.UUID


@Entity
data class RunModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val _id: String =  UUID.randomUUID().toString(),
    @SerializedName("unitType")
    val unitType: String = "N/A",
    @SerializedName("distanceAmount")
    val distanceAmount: Int = 0,
    var message: String = "Go Hun!",
    @SerializedName("dateRan")
    val dateRan: Date = Date(),
    var email: String = "homer@gmail.com"
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

