package ie.setu.placemark.ui.components.badges

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Text
import ie.setu.placemark.data.model.UserProfileModel

@Composable
fun BadgeCarousel(userProfile: UserProfileModel) {
    val awardedBadges = badges.filter { it.condition(userProfile) }

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        items(awardedBadges) { badge ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .width(100.dp)
                    .padding(8.dp)
            ) {
                Image(
                    painter = painterResource(id = badge.imageRes),
                    contentDescription = badge.name,
                    modifier = Modifier.size(80.dp)
                )
                Text(
                    text = badge.name,
                    style = TextStyle(fontSize = 12.sp, color = Color.Black),
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}
