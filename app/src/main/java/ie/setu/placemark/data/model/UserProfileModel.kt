package ie.setu.placemark.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class UserProfileModel(
    @PrimaryKey(autoGenerate = true)
    val userId: Int = 0,
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
    val profilePictureUrl: String?,
    @SerializedName("totalDistanceRun")
    val totalDistanceRun: Double = 0.0,
    @SerializedName("totalRuns")
    val totalRuns: Int = 0,
    @SerializedName("averagePace")
    val averagePace: Double = 0.0,
    @SerializedName("preferredUnit")
    val preferredUnit: String = "km" // Default unit
)
