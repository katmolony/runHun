package ie.setu.placemark.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.DocumentId
import com.google.gson.annotations.SerializedName

@Entity
data class UserProfileModel(
    @PrimaryKey(autoGenerate = true)
    val userId: Int = 0,
    @DocumentId val _id: String = "N/A",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("email")
    val email: String = "",
    val profilePictureUrl: String? = null,
    @SerializedName("totalDistanceRun")
    val totalDistanceRun: Double = 0.0,
    @SerializedName("totalRuns")
    val totalRuns: Int = 0,
    @SerializedName("averagePace")
    val averagePace: Double = 0.0,
    @SerializedName("preferredUnit")
    val preferredUnit: String = "km" // Default unit
) {
    // No-argument constructor for Firestore
    constructor() : this(0, "N/A", "", "", null, 0.0, 0, 0.0, "km")
}