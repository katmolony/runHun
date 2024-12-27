package ie.setu.placemark.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ie.setu.placemark.ui.screens.about.AboutScreen
import ie.setu.placemark.ui.screens.achievements.AchievementsScreen
import ie.setu.placemark.ui.screens.home.HomeScreen
import ie.setu.placemark.ui.screens.login.LoginScreen
import ie.setu.placemark.ui.screens.profile.ProfileScreen
import ie.setu.placemark.ui.screens.register.RegisterScreen
import ie.setu.placemark.ui.screens.details.DetailsScreen
import ie.setu.placemark.ui.screens.options.OptionsScreen
import ie.setu.placemark.ui.screens.report.ReportScreen
import ie.setu.placemark.ui.screens.run.RunScreen

@Composable
fun NavHostProvider(
    modifier: Modifier,
    navController: NavHostController,
    startDestination: AppDestination,
    paddingValues: PaddingValues,
//    runs: SnapshotStateList<RunModel>
) {
    NavHost(
        navController = navController,
        startDestination = startDestination.route,
        modifier = Modifier.padding(paddingValues = paddingValues)) {

        composable(route = Run.route) {
            //call our 'Run' Screen Here
            RunScreen(modifier = modifier)
        }

        composable(route = Home.route) {
            //call our 'Home' Screen Here
            HomeScreen(modifier = modifier)
        }

        composable(route = Report.route) {
            //call our 'Report' Screen Here
            ReportScreen(
                modifier = modifier,
                    onClickRunDetails = {
                        runId : String ->
                        navController.navigateToRunDetails(runId)
                    },
                )
        }

        //ui screenn for options
        composable(route = Options.route) {
            OptionsScreen(
                modifier = modifier,
                navController = navController
            )
        }

        //ui screenn for about
        composable(route = About.route) {
            //call our 'Option' Screen Here
            AboutScreen(modifier = modifier)
        }

        composable(route = Login.route) {
            //call our 'Login' Screen Here
            LoginScreen(
                navController = navController,
                onLogin = { navController.popBackStack() }
            )
        }

        composable(route = Register.route) {
            //call our 'Register' Screen Here
            RegisterScreen(
                navController = navController,
                onRegister = { navController.popBackStack() }
            )
        }

        composable(
            route = Details.route,
            arguments = Details.arguments
        )
        { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getString(Details.idArg)
            if (id != null) {
                DetailsScreen()
            }
        }

        composable(route = Profile.route) {
            ProfileScreen(
                onSignOut = {
                    navController.popBackStack()
                    navController.navigate(Login.route) {
                        popUpTo(Home.route) { inclusive = true }
                    }
                },
            )
        }

        composable(route = Achievements.route) {
            //call our 'Achievements' Screen Here
            AchievementsScreen(
                modifier = modifier,
                onClickRunDetails = { runId: String ->
                    navController.navigateToRunDetails(runId)
                },
            )
        }
    }
}
private fun NavHostController.navigateToRunDetails(runId: String) {
    this.navigate("details/$runId")
}

