# RunHun

Run is an Android application for tracking and managing exercise details. The app is built using Kotlin, Jetpack Compose, and Dagger Hilt for dependency injection.

## Features

- View exercise details
- Update exercise messages
- Store data using Room database
- Dependency injection with Dagger Hilt
- Display weekly run statistics using MPAndroidChart
- Authentication with Firebase
- Navigation with Jetpack Navigation Compose
- Material Design 3 (Material You) components

## Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/katmolony/placemark.git
    ```
2. Open the project in Android Studio.
3. Build the project to download dependencies and generate necessary files.

## Usage

1. Run the app on an emulator or a physical device.
2. Navigate through the app to view and update exercise details.

## Project Structure

- `app/src/main/java/ie/setu/placemark/data/room`: Contains Room database setup and repository.
- `app/src/main/java/ie/setu/placemark/ui/components/details`: Contains UI components for displaying and updating exercise details.
- `app/src/main/java/ie/setu/placemark/ui/screens/details`: Contains ViewModel for managing UI-related data in the details screen.
- `app/src/main/java/ie/setu/placemark/ui/screens/report`: Contains ViewModel for managing UI-related data in the report screen.

## Dependencies

- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Dagger Hilt](https://dagger.dev/hilt/)
- [Room](https://developer.android.com/training/data-storage/room)
- [MPAndroidChart](https://github.com/PhilJay/MPAndroidChart)
- [Firebase](https://firebase.google.com/)
- [Jetpack Navigation Compose](https://developer.android.com/jetpack/compose/navigation)
- [Material Design 3](https://m3.material.io/)

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.