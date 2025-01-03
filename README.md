# RunHun

Run is an Android application for tracking and managing exercise details. The app is built using Kotlin, Jetpack Compose, and Dagger Hilt for dependency injection.

## Technical Report: Functionality Overview
This section outlines the key functionalities of the application, including its components, persistence mechanisms, user experience (UX) features, developer experience (DX) improvements, and integration with third-party libraries and APIs.

### Core Functionalities

1. CRUD for Models 
   - Description: Supports full Create, Read, Update, and Delete operations for Run and User models. 
   - Implementation: Data is stored and retrieved from Firebase Firestore with optional local caching through Room or temporary JSON storage. 

2. Authentication 
   - Description: Implements user authentication through Firebase Authentication, including support for Google Sign-In.
   - Libraries Used:
     - Firebase Authentication (firebase.auth)
     - Google Play Services Auth (play.services.auth)
   
3. Sorting and Filtering 
   - Description: Allows sorting of runs by date or distance in ascending and descending order. 
   - Persistence: Run data is stored in Firebase Firestore and queried dynamically for sorting.

4. Graphs for Run Statistics 
   - Description: Visualizes running data (e.g., distance and pace) using MPAndroidChart. 
   - Libraries Used:
     - MPAndroidChart (com.github.PhilJay:MPAndroidChart:3.1.0)

5. Badge and Achievement System 
   - Description: Displays user achievements with image badges using Coil for efficient image loading. 
   - Libraries Used:
     - Coil for image loading and carousel (coil.compose, coil-svg).

6. Theming and Preferences 
   - Description:
     - Supports toggle functionality for dark mode and unit preferences (e.g., metric vs. imperial). 
     - Dark mode is set directly on the profile screen. 
   - Persistence: Preferences are saved using Android DataStore.

7. Custom UI Components 
   - Description: Includes custom UI elements such as navigation bars (top and bottom), dynamic graphs, and image carousels for an enhanced user experience.

### Persistence Mechanisms

1. Firebase Firestore 
   - Description: Serves as the primary backend for storing User and Run data, ensuring real-time sync across devices.
   
2. Room Database 
   - Description: Provides local offline storage for temporary data and caching.

3. DataStore Preferences 
   - Description: Stores lightweight settings like theme and unit preferences persistently.

### User Experience (UX)

1. Navigation 
   - Provides seamless navigation via a top and bottom app bar. 
   - Includes navigation to all core screens: Home, Reports, and Profile.

2. Dark Mode and Custom Themes 
   - Offers a toggle for dark mode across the app to enhance usability in different lighting conditions.

3. Visualizations 
   - Graphs displaying user performance metrics ensure data-driven insights. 
   - Image badges and carousel UI components create engaging user interactions.

4. Tagged Releases 
   - Ensures a stable UX through versioned releases: v1.0.0, v2.0.0, and beyond.
   
### Developer Experience (DX)

1. MVVM Design Pattern 
   - Utilizes the Model-View-ViewModel (MVVM) architecture to separate concerns and facilitate maintainability.

2. Branching Strategy 
   - Includes a dev branch for feature development and a release branch for production-ready versions (e.g., v2, v3, v4).

3. Git Commit History 
   - Maintains a clean and well-documented commit history for easier collaboration and version control.

### Third-Party Libraries and APIs

1. Firebase:
   - Authentication (firebase.auth, firebase.auth.ktx). 
   - Firestore Database (firebase.firestore).

2. Google APIs:
   - Google Play Services Auth for Google Sign-In.

3. MPAndroidChart:
   - Provides graphing capabilities for run statistics visualization.

4. Coil:
   - Efficient image loading and rendering for badges and UI elements.

5. Retrofit and Gson:
   - Used for network calls and JSON parsing (future expansion).

6. Jetpack Components:
   - Navigation (androidx.navigation.compose). 
   - ViewModel (androidx.lifecycle.viewmodel). 
   - Room Database (androidx.room). 
   - DataStore Preferences (androidx.datastore).

7. Hilt:
   - Simplifies dependency injection for cleaner code and modular architecture.

## Personal Statement

Working on this application has been an immensely rewarding and enjoyable experience. From conceptualizing the core functionalities to implementing advanced features like Firebase integration, custom UI components, and data visualization, I thoroughly enjoyed every step of the development process. It was fascinating to see how different technologies and design patterns came together to create a seamless, user-friendly application.

One of the most interesting aspects of this project was exploring third-party libraries such as MPAndroidChart and Coil, which allowed me to add dynamic visualizations and engaging features like badge carousels. Additionally, implementing Firebase Authentication and Firestore taught me the importance of scalable and reliable backend services, while working with Jetpack components and the MVVM architecture deepened my understanding of modern Android development practices.

Beyond the technical learning, this project reinforced my passion for problem-solving and creating meaningful user experiences. It was fulfilling to see the app evolve from a simple idea into a polished product that aligns with industry standards. I truly enjoyed the challenge of balancing functionality with aesthetics and ensuring that the application was not only efficient but also intuitive for users.

This experience has strengthened my skills in software development and boosted my confidence in tackling complex projects. It has also reaffirmed my enthusiasm for mobile development and my eagerness to keep exploring new technologies in the future.

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

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.