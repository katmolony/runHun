package ie.setu.placemark.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ie.setu.placemark.data.RunModel
import ie.setu.placemark.ui.screens.ScreenRun
import ie.setu.placemark.ui.screens.ScreenReport
import ie.setu.placemark.ui.screens.ScreenOptions

@Composable
fun NavHostProvider(
    modifier: Modifier,
    navController: NavHostController,
    paddingValues: PaddingValues,
    runs: SnapshotStateList<RunModel>
) {
    NavHost(
        navController = navController,
        startDestination = Report.route,
        modifier = Modifier.padding(paddingValues = paddingValues)) {

        composable(route = Run.route) {
            //call our 'Run' Screen Here
            ScreenRun(modifier = modifier,runs = runs)
        }
        composable(route = Report.route) {
            //call our 'Report' Screen Here
            ScreenReport(modifier = modifier, runs = runs)
        }
        // Add option later: 9a lab 10 a
        //ui screenn for options
        composable(route = Options.route) {
            //call our 'Option' Screen Here
            ScreenOptions(modifier = modifier)
        }
    }
}
