package ie.setu.placemark.ui.components.badges

import androidx.annotation.DrawableRes
import ie.setu.placemark.data.model.UserProfileModel

data class Badge(
    val name: String,
    @DrawableRes val imageRes: Int,
    val condition: (UserProfileModel) -> Boolean
)

