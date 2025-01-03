package ie.setu.placemark.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Details
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument

interface AppDestination {
    val icon: ImageVector
    val label: String
    val route: String
}

object Report : AppDestination {
    override val icon = Icons.AutoMirrored.Filled.List
    override val label = "Report"
    override val route = "report"
}

object Run : AppDestination {
    override val icon = Icons.Filled.AddCircle
    override val label = "Run"
    override val route = "run"
}

object About : AppDestination {
    override val icon = Icons.Filled.Info
    override val label = "About"
    override val route = "about"
}

object Options : AppDestination {
    override val icon = Icons.Filled.Menu
    override val label = "Options"
    override val route = "options"
}

object Details : AppDestination {
    override val icon = Icons.Filled.Details
    override val label = "Details"
    const val idArg = "id"
    override val route = "details/{$idArg}"
    val arguments = listOf(
        navArgument(idArg) {type = NavType.StringType }
    )
}

object Home : AppDestination {
    override val icon = Icons.Filled.Home
    override val label = "Home"
    override val route = "home"
}

object Profile : AppDestination {
    override val icon = Icons.Default.Person
    override val label = "Profile"
    override val route = "Profile"
}

object Login : AppDestination {
    override val icon = Icons.AutoMirrored.Filled.Login
    override val label = "Login"
    override val route = "Login"
}

object Register : AppDestination {
    override val icon = Icons.Default.AccountCircle
    override val label = "Register"
    override val route = "Register"
}

object Achievements : AppDestination {
    override val icon = Icons.Default.AccountCircle
    override val label = "Achievements"
    override val route = "Achievements"
}

object Settings : AppDestination {
    override val icon = Icons.Default.AccountCircle
    override val label = "Settings"
    override val route = "Settings"
}

object Contact : AppDestination {
    override val icon = Icons.Default.AccountCircle
    override val label = "Contact"
    override val route = "Contact"
}

val bottomAppBarDestinations = listOf(Run, Report, Options, Profile)
val userSignedOutDestinations = listOf(Login, Register)
val allDestinations = listOf(
    Report,
    Run,
    Options,
    Details,
    Home,
    Profile,
    Login,
    Register,
    About,
    Achievements,
    Settings,
    Contact
)
