package ie.setu.placemark.ui.components.profile

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ie.setu.placemark.R
import ie.setu.placemark.ui.theme.RunHunTheme

// Data class to represent the user profile
data class UserProfile(
    val totalDistanceRun: Double,
    val totalRuns: Int,
    val averagePace: Double,
    val preferredUnit: String
)

@Composable
fun ProfileCard(
    profile: UserProfile,
    onClickEdit: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 2.dp)
    ) {
        ProfileCardContent(
            profile = profile,
            onClickEdit = onClickEdit
        )
    }
}

@Composable
private fun ProfileCardContent(
    profile: UserProfile,
    onClickEdit: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .padding(12.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(4.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Profile Status",
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    text = "User Stats",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold
                    )
                )
                Spacer(Modifier.weight(1f))
            }

            // Display the profile details with titles
            ProfileField("Total Distance Run", "${profile.totalDistanceRun} ${profile.preferredUnit}")
            ProfileField("Total Runs", profile.totalRuns.toString())
            ProfileField("Average Pace", "${profile.averagePace} ${profile.preferredUnit}/min")
            ProfileField("Preferred Unit", profile.preferredUnit)

            if (expanded) {
                Text(modifier = Modifier.padding(vertical = 16.dp), text = "Profile Details")
            }
        }
    }
//    IconButton(onClick = { expanded = !expanded }) {
//        Icon(
//            imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
//            contentDescription = if (expanded) stringResource(R.string.show_less) else stringResource(R.string.show_more)
//        )
//    }
}

@Composable
fun ProfileField(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview
@Composable
fun ProfileCardPreview() {
    val userProfile = UserProfile(
        totalDistanceRun = 150.5,
        totalRuns = 20,
        averagePace = 5.3,
        preferredUnit = "km"
    )
    RunHunTheme {
        ProfileCard(
            profile = userProfile,
            onClickEdit = {}
        )
    }
}
