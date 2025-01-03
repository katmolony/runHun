package ie.setu.placemark.ui.screens.home

import android.annotation.SuppressLint
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import ie.setu.placemark.navigation.Home
import ie.setu.placemark.navigation.Login
import ie.setu.placemark.navigation.NavHostProvider
import ie.setu.placemark.navigation.Options
import ie.setu.placemark.navigation.Report
import ie.setu.placemark.navigation.allDestinations
import ie.setu.placemark.navigation.bottomAppBarDestinations
import ie.setu.placemark.navigation.userSignedOutDestinations
import ie.setu.placemark.ui.components.general.BottomAppBarProvider
import ie.setu.placemark.ui.components.general.TopAppBarProvider
import ie.setu.placemark.ui.screens.map.MapViewModel
import ie.setu.placemark.ui.theme.RunHunTheme
import ie.setu.placemark.ui.theme.ThemeViewModel
import android.Manifest
import androidx.compose.runtime.LaunchedEffect

@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(modifier: Modifier = Modifier,
               homeViewModel: HomeViewModel = hiltViewModel(),
               navController: NavHostController = rememberNavController(),
               themeViewModel: ThemeViewModel,
               mapViewModel: MapViewModel = hiltViewModel(),
) {
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentNavBackStackEntry?.destination
    val currentBottomScreen =
        allDestinations.find { it.route == currentDestination?.route } ?: Login
    var startScreen = currentBottomScreen

    val currentUser = homeViewModel.currentUser
    val isActiveSession = homeViewModel.isAuthenticated()

    val userEmail = if (isActiveSession) currentUser?.email else ""
    val userName = if (isActiveSession) currentUser?.displayName else ""

    val locationPermissions = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    )

//     Use safe calls and fallback to empty string if no active session
//    val userEmail = currentUser?.email ?: ""
//    val userName = currentUser?.displayName ?: ""

    val userDestinations = if (!isActiveSession)
        userSignedOutDestinations
    else bottomAppBarDestinations

    if (isActiveSession) {
        startScreen = Report
        LaunchedEffect(true) {
            locationPermissions.launchMultiplePermissionRequest()
            if (locationPermissions.allPermissionsGranted) {
                mapViewModel.getLocationUpdates()
            }
        }
    }

    val isDarkTheme = themeViewModel.isDarkTheme.collectAsState().value

    RunHunTheme(darkTheme = isDarkTheme) {
        Scaffold(
            modifier = modifier,
            topBar = {
                TopAppBarProvider(
                    navController = navController,
                    currentScreen = currentBottomScreen,
                    canNavigateBack = navController.previousBackStackEntry != null,
                    email = userEmail!!,
                    name = userName!!
                ) { navController.navigateUp() }
            },
            content = { paddingValues ->
                NavHostProvider(
                    modifier = modifier,
                    navController = navController,
                    startDestination = startScreen,
                    paddingValues = paddingValues,
                    themeViewModel = themeViewModel
                )
            },
            bottomBar = {
                BottomAppBarProvider(
                    navController,
                    currentScreen = currentBottomScreen,
                    userDestinations
                )
            }
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun MyAppPreview() {
//    RunHunTheme {
//        HomeScreen(modifier = Modifier),
//        themeViewModel= ThemeViewModel
//    }
//}