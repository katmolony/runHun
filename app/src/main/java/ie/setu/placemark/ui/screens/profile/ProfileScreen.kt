package ie.setu.placemark.ui.screens.profile

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ie.setu.placemark.R
import ie.setu.placemark.data.model.RunModel
import ie.setu.placemark.ui.components.general.HeadingTextComponent
import ie.setu.placemark.ui.components.general.ShowPhotoPicker
import ie.setu.placemark.ui.components.profile.UserProfile
import ie.setu.placemark.ui.screens.login.LoginViewModel
import ie.setu.placemark.ui.screens.register.RegisterViewModel
import ie.setu.placemark.ui.components.profile.ProfileCard
import ie.setu.placemark.ui.theme.RunHunTheme
import ie.setu.placemark.ui.theme.ThemeViewModel

@Composable
fun ProfileScreen(
    onSignOut: () -> Unit = {},
    profileViewModel: ProfileViewModel = hiltViewModel(),
    loginViewModel: LoginViewModel = hiltViewModel(),
    registerViewModel: RegisterViewModel = hiltViewModel(),
    themeViewModel: ThemeViewModel
) {
    val userProfile = profileViewModel.userProfile.value
    var photoUri: Uri? by remember { mutableStateOf(profileViewModel.photoUri) }
    val isDarkTheme = themeViewModel.isDarkTheme.collectAsState().value

    RunHunTheme(darkTheme = isDarkTheme) {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            HeadingTextComponent(value = stringResource(id = R.string.account_settings))
            Spacer(modifier = Modifier.height(10.dp))

            if (profileViewModel.isLoading.value) {
                Text("Loading profile...")
            } else if (profileViewModel.isErr.value) {
                Text("Error fetching profile. Please try again.")
            } else {
                if (photoUri.toString().isNotEmpty())
                    ProfileContent(
                        photoUri = photoUri,
                        displayName = profileViewModel.displayName,
                        email = profileViewModel.email
                    )
                else
                    BasicContent(
                        displayName = profileViewModel.displayName,
                        email = profileViewModel.email
                    )

                ShowPhotoPicker(
                    onPhotoUriChanged = {
                        photoUri = it
                        profileViewModel.updatePhotoUri(photoUri!!)
                    }
                )

                Spacer(modifier = Modifier.height(10.dp))

                if (userProfile != null) {
                    ProfileCard(
                        profile = UserProfile(
                            totalDistanceRun = userProfile.totalDistanceRun ?: 0.0,
                            totalRuns = userProfile.totalRuns ?: 0,
                            averagePace = userProfile.averagePace ?: 0.0,
                            preferredUnit = userProfile.preferredUnit ?: "km"
                        ),
                        onClickEdit = { /* Handle edit action if needed */ }
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        profileViewModel.signOut()
                        onSignOut()
                        loginViewModel.resetLoginFlow()
                        registerViewModel.resetRegisterFlow()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                ) {
                    Text(text = "Logout")
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(onClick = { themeViewModel.toggleTheme() }) {
                    Text(text = if (isDarkTheme) "Switch to Light Theme" else "Switch to Dark Theme")
                }
            }
        }
    }
}