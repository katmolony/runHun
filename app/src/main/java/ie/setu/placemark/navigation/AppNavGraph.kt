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
import ie.setu.placemark.ui.screens.contact.ContactScreen
import ie.setu.placemark.ui.screens.home.HomeScreen
import ie.setu.placemark.ui.screens.login.LoginScreen
import ie.setu.placemark.ui.screens.profile.ProfileScreen
import ie.setu.placemark.ui.screens.register.RegisterScreen
import ie.setu.placemark.ui.screens.details.DetailsScreen
import ie.setu.placemark.ui.screens.options.OptionsScreen
import ie.setu.placemark.ui.screens.report.ReportScreen
import ie.setu.placemark.ui.screens.run.RunScreen
import ie.setu.placemark.ui.screens.settings.SettingsScreen
import ie.setu.placemark.ui.screens.settings.SettingsViewModel
import ie.setu.placemark.ui.screens.map.MapScreen
import ie.setu.placemark.ui.theme.ThemeViewModel

@Composable
fun NavHostProvider(
    modifier: Modifier,
    navController: NavHostController,
    startDestination: AppDestination,
    paddingValues: PaddingValues,
    themeViewModel: ThemeViewModel
) {
    NavHost(
        navController = navController,
        startDestination = startDestination.route,
        modifier = Modifier.padding(paddingValues = paddingValues)
    ) {
        composable(route = Run.route) {
            RunScreen(modifier = modifier)
        }

        composable(route = Home.route) {
            HomeScreen(modifier = modifier, themeViewModel = themeViewModel)
        }

        composable(route = Report.route) {
            ReportScreen(
                modifier = modifier,
                onClickRunDetails = { runId: String ->
                    navController.navigateToRunDetails(runId)
                }
            )
        }

        composable(route = Options.route) {
            OptionsScreen(
                modifier = modifier,
                navController = navController
            )
        }

//        composable(route = Settings.route) {
//            SettingsScreen(
//                modifier = modifier,
//                settingsViewModel = SettingsViewModel(themeViewModel)
//            )
//        }

        composable(route = Settings.route) {
            SettingsScreen(modifier = modifier)
        }

        composable(route = Contact.route) {
            ContactScreen(modifier = modifier)
        }

        composable(route = About.route) {
            AboutScreen(modifier = modifier)
        }

        composable(route = Login.route) {
            LoginScreen(
                navController = navController,
                onLogin = { navController.popBackStack() }
            )
        }

        composable(route = Register.route) {
            RegisterScreen(
                navController = navController,
                onRegister = { navController.popBackStack() }
            )
        }

        composable(
            route = Details.route,
            arguments = Details.arguments
        ) { navBackStackEntry ->
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
                themeViewModel = themeViewModel
            )
        }

        composable(route = Achievements.route) {
            AchievementsScreen(
                modifier = modifier,
                onClickRunDetails = { runId: String ->
                    navController.navigateToRunDetails(runId)
                }
            )
        }

        composable(route = Map.route) {
            //call our 'Map' Screen Here
            MapScreen()
        }
    }
}

private fun NavHostController.navigateToRunDetails(runId: String) {
    this.navigate("details/$runId")
}