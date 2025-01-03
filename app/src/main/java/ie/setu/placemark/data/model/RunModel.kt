package ie.setu.placemark.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.DocumentId
import com.google.gson.annotations.SerializedName
import java.util.Date
import java.util.UUID

@Entity
data class RunModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @DocumentId val _id: String = "N/A",
    @SerializedName("unitType")
    val unitType: String = "N/A",
    @SerializedName("distanceAmount")
    val distanceAmount: Int = 0,
    var message: String = "Go Hun!",
    @SerializedName("dateRan")
    val dateRan: Date = Date(),
    var email: String = "homer@gmail.com",
    var imageUri: String = "",
    var latitude: Double = 0.0,
    var longitude: Double = 0.0

) {
    // No-argument constructor for Firestore
    constructor() : this(0, "", "", 0, "", Date(), "")
}

val fakeRuns = List(5) { i ->
    RunModel(id = 12345 + i,
        _id = "65629$i",
        "metre $i",
        i.toInt(),
        "Message $i",
        Date()
    )
}

