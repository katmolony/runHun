//package ie.setu.placemark.ui.components.achievements
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Star
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
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
//import androidx.hilt.navigation.compose.hiltViewModel
//import ie.setu.placemark.ui.components.general.ShowError
//import ie.setu.placemark.ui.components.general.ShowLoader
//import ie.setu.placemark.ui.screens.achievements.AchievementsViewModel
//
//@Composable
//fun AchievementsScreen(
//    achievementsViewModel: AchievementsViewModel = hiltViewModel()
//) {
//    val mostRecentRun = achievementsViewModel.mostRecentRun.value
//    val longestRun = achievementsViewModel.longestRun.value
//    val isLoading = achievementsViewModel.isLoading.value
//    val isError = achievementsViewModel.isError.value
//    val error = achievementsViewModel.error.value
//
//    LaunchedEffect(Unit) {
//        achievementsViewModel.getAchievements()
//    }
//
//    Column(modifier = Modifier.padding(16.dp)) {
//        if (isLoading) {
//            ShowLoader("Loading Achievements...")
//        } else if (isError) {
//            ShowError(
//                headline = "Error fetching achievements",
//                subtitle = error.message ?: "Unknown error",
//                onClick = { achievementsViewModel.getAchievements() }
//            )
//        } else {
//            if (mostRecentRun != null) {
//                AchievementCard(
//                    title = "Most Recent Run",
//                    message = mostRecentRun.message,
//                    distanceAmount = mostRecentRun.distanceAmount,
//                    unitType = mostRecentRun.unitType,
//                    dateRan = mostRecentRun.dateRan
//                )
//                Spacer(modifier = Modifier.height(16.dp))
//            }
//
//            if (longestRun != null) {
//                AchievementCard(
//                    title = "Longest Run",
//                    message = longestRun.message,
//                    distanceAmount = longestRun.distanceAmount,
//                    unitType = longestRun.unitType,
//                    dateRan = longestRun.dateRan
//                )
//            }
//        }
//    }
//}
