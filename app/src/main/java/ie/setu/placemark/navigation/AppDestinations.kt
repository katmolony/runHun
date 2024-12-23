package ie.setu.placemark.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Details
import androidx.compose.material.icons.filled.Menu
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
        navArgument(idArg) { type = NavType.IntType }
    )
}


val bottomAppBarDestinations = listOf(Run, Report, Options)
val allDestinations = listOf(Report, Run, Options, Details)
