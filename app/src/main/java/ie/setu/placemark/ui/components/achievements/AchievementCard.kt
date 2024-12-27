//package ie.setu.placemark.ui.components.achievements
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Star
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import ie.setu.placemark.data.model.RunModel
//import ie.setu.placemark.data.model.fakeRuns
//import ie.setu.placemark.ui.theme.RunHunTheme
//import java.text.DateFormat
//import java.util.Date
//
//@Composable
//fun AchievementCard(
//    title: String,
//    message: String,
//    distanceAmount: Int,
//    unitType: String,
//    dateRan: Date,
//    modifier: Modifier = Modifier
//) {
//    Card(
//        modifier = modifier
//            .fillMaxWidth()
//            .padding(8.dp),
//        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
//        elevation = CardDefaults.cardElevation(4.dp)
//    ) {
//        Column(
//            modifier = Modifier
//                .padding(16.dp)
//                .fillMaxWidth()
//        ) {
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Icon(
//                    imageVector = Icons.Filled.Star,
//                    contentDescription = "Achievement Icon",
//                    tint = Color(0xFFFFC107),
//                    modifier = Modifier.size(24.dp)
//                )
//                Spacer(modifier = Modifier.width(8.dp))
//                Text(
//                    text = title,
//                    style = MaterialTheme.typography.titleMedium,
//                    fontSize = 20.sp
//                )
//            }
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(
//                text = "Message: $message",
//                style = MaterialTheme.typography.bodyMedium
//            )
//            Text(
//                text = "Distance: $distanceAmount $unitType",
//                style = MaterialTheme.typography.bodyMedium
//            )
//            Text(
//                text = "Date: ${DateFormat.getDateInstance().format(dateRan)}",
//                style = MaterialTheme.typography.bodySmall
//            )
//        }
//    }
//}
//
////@Preview(showBackground = true)
////@Composable
////fun AchievementCardPreview() {
////    val sampleRun = RunModel(
////        id = 1,
////        _id = "001",
////        unitType = "meters",
////        distanceAmount = 5000,
////        message = "Great job!",
////        dateRan = Date()
////    )
////    RunHunTheme {
////        AchievementCard(
////            title = "Most Recent Run",
////            message = sampleRun.message,
////            distanceAmount = sampleRun.distanceAmount,
////            unitType = sampleRun.unitType,
////            dateRan = sampleRun.dateRan
////        )
////    }
////}
