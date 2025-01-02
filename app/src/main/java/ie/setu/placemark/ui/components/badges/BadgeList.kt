package ie.setu.placemark.ui.components.badges

import ie.setu.placemark.R

val badges = listOf(
    Badge(
        name = "100KM Club",
        imageRes = R.drawable.badge,
        condition = { user -> user.totalDistanceRun >= 100.0 }
    ),
    Badge(
        name = "10 Runs",
        imageRes = R.drawable.finish_line,
        condition = { user -> user.totalRuns >= 10 }
    ),
    Badge(
        name = "Speedster",
        imageRes = R.drawable.winner,
        condition = { user -> user.averagePace <= 50.0 }
    )
)


