package ie.setu.placemark.models

data class RunModel(var title : String = "") {
}

//id: Unique identifier.
//date: Date and time of the run.
//distance: Total distance covered (e.g., in kilometers or miles).
//duration: Total time of the session.
//averagePace: Average pace (e.g., minutes per kilometer).
//caloriesBurned: Estimated calories burned.
//path: List of Location points to map the route.
