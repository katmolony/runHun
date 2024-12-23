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
import ie.setu.placemark.ui.screens.options.OptionsScreen
import ie.setu.placemark.ui.screens.report.ReportScreen
import ie.setu.placemark.ui.screens.run.RunScreen

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
            RunScreen(modifier = modifier)
        }
        composable(route = Report.route) {
            //call our 'Report' Screen Here
            ReportScreen(modifier = modifier)
        }
        // Add option later: 9a lab 10 a
        //ui screenn for options
        composable(route = Options.route) {
            //call our 'Option' Screen Here
            OptionsScreen(modifier = modifier)
        }
    }
}
