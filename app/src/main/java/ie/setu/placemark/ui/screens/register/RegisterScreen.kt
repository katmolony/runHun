package ie.setu.placemark.ui.screens.register

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ie.setu.placemark.firebase.auth.Response
import ie.setu.placemark.navigation.Home
import ie.setu.placemark.navigation.Register
import ie.setu.placemark.ui.components.general.ButtonComponent
import ie.setu.placemark.ui.components.general.CheckboxComponent
import ie.setu.placemark.ui.components.general.ClickableLoginTextComponent
import ie.setu.placemark.ui.components.general.DividerTextComponent
import ie.setu.placemark.ui.components.general.HeadingLogoComponent
import ie.setu.placemark.ui.components.general.HeadingTextComponent
import ie.setu.placemark.ui.components.general.MyTextFieldComponent
import ie.setu.placemark.ui.components.general.NormalTextComponent
import ie.setu.placemark.ui.components.general.PasswordTextFieldComponent
import ie.setu.placemark.ui.components.general.ShowLoader
import ie.setu.placemark.R
import ie.setu.placemark.RunHunMainActivity

@Composable
fun PreferredUnitSelection(
    currentUnit: String,
    onUnitSelected: (String) -> Unit
) {
    Row {
        RadioButton(
            selected = currentUnit == "km",
            onClick = { onUnitSelected("km") }
        )
        Text(text = "Kilometers", modifier = Modifier.clickable { onUnitSelected("km") })
        Spacer(modifier = Modifier.width(16.dp))
        RadioButton(
            selected = currentUnit == "miles",
            onClick = { onUnitSelected("miles") }
        )
        Text(text = "Miles", modifier = Modifier.clickable { onUnitSelected("miles") })
    }
}

@Composable
fun RegisterScreen(
    onRegister: () -> Unit = {},
    navController: NavController,
    registerViewModel: RegisterViewModel = hiltViewModel(),
    signInWithGoogle: () -> Unit
) {
    val registerFlow = registerViewModel.signupFlow.collectAsState()
    val context = LocalContext.current

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(28.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                MyTextFieldComponent(
                    labelValue = stringResource(id = R.string.first_name),
                    painterResource(id = R.drawable.profile),
                    onTextChanged = {
                        registerViewModel.onEvent(RegisterUIEvent.FirstNameChanged(it))
                    },
                    errorStatus = registerViewModel.registrationUIState.value.firstNameError
                )
                MyTextFieldComponent(
                    labelValue = stringResource(id = R.string.email),
                    painterResource = painterResource(id = R.drawable.message),
                    onTextChanged = {
                        registerViewModel.onEvent(RegisterUIEvent.EmailChanged(it))
                    },
                    errorStatus = registerViewModel.registrationUIState.value.emailError
                )
                PasswordTextFieldComponent(
                    labelValue = stringResource(id = R.string.password),
                    painterResource = painterResource(id = R.drawable.lock),
                    onTextSelected = {
                        registerViewModel.onEvent(RegisterUIEvent.PasswordChanged(it))
                    },
                    errorStatus = registerViewModel.registrationUIState.value.passwordError
                )
                PreferredUnitSelection(
                    currentUnit = registerViewModel.registrationUIState.value.preferredUnit,
                    onUnitSelected = {
                        registerViewModel.onEvent(RegisterUIEvent.PreferredUnitChanged(it))
                    }
                )
                CheckboxComponent(
                    value = stringResource(id = R.string.terms_and_conditions),
                    onTextSelected = {},
                    onCheckedChange = {
                        registerViewModel.onEvent(RegisterUIEvent.PrivacyPolicyCheckBoxClicked(it))
                    }
                )
                Spacer(modifier = Modifier.height(20.dp))
                ButtonComponent(
                    value = stringResource(id = R.string.register),
                    onButtonClicked = {
                        registerViewModel.onEvent(RegisterUIEvent.RegisterButtonClicked)
                        onRegister()
                    },
                    isEnabled = registerViewModel.allValidationsPassed.value
                )
                Spacer(modifier = Modifier.height(20.dp))
                Button(onClick = {
                    signInWithGoogle()
                }) {
                    Text(text = "Sign in with Google")
                }
                Spacer(modifier = Modifier.height(20.dp))
            }

            if (registerViewModel.signUpInProgress.value) {
                CircularProgressIndicator()
            }
        }

        registerFlow.value?.let {
            when (it) {
                is Response.Success -> {
                    LaunchedEffect(Unit) {
                        navController.navigate(Home.route) {
                            popUpTo(Register.route) {
                                inclusive = true
                            }
                        }
                    }
                }
                is Response.Failure -> {
                    Toast.makeText(context, "Sign-In Failed: ${it.e.message}", Toast.LENGTH_LONG).show()
                }
                is Response.Loading -> {
                    ShowLoader(message = "Signing in...")
                }
            }
        }
    }
}

//@Preview
//@Composable
//fun DefaultPreviewOfSignUpScreen() {
//    PreviewRegisterScreen()
//}
//
//@Composable
//fun PreviewRegisterScreen() {
//
//    Box(
//        modifier = Modifier.fillMaxSize(),
//        contentAlignment = Alignment.Center
//    ) {
//
//        Surface(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color.White)
//                .padding(28.dp)
//        ) {
//            Column(modifier = Modifier.fillMaxSize()) {
//
//                NormalTextComponent(value = stringResource(id = R.string.register))
//                HeadingTextComponent(value = stringResource(id = R.string.create_account))
//                Spacer(modifier = Modifier.height(20.dp))
//                HeadingLogoComponent()
//                Spacer(modifier = Modifier.height(20.dp))
//
//                MyTextFieldComponent(
//                    labelValue = stringResource(id = R.string.first_name),
//                    painterResource(id = R.drawable.profile),
//                    onTextChanged = {
//                      //  registerViewModel.onEvent(RegisterUIEvent.FirstNameChanged(it))
//                    },
//                    //errorStatus = registerViewModel.registrationUIState.value.firstNameError
//                    errorStatus = true
//                )
//
//                MyTextFieldComponent(
//                    labelValue = stringResource(id = R.string.email),
//                    painterResource = painterResource(id = R.drawable.message),
//                    onTextChanged = {
//                        //registerViewModel.onEvent(RegisterUIEvent.EmailChanged(it))
//                    },
//                    errorStatus = true
//                )
//
//                PasswordTextFieldComponent(
//                    labelValue = stringResource(id = R.string.password),
//                    painterResource = painterResource(id = R.drawable.lock),
//                    onTextSelected = {
//                     //   registerViewModel.onEvent(RegisterUIEvent.PasswordChanged(it))
//                    },
//                   // errorStatus = registerViewModel.registrationUIState.value.passwordError
//                    errorStatus = true
//                )
//
//                CheckboxComponent(value = stringResource(id = R.string.terms_and_conditions),
//                    onTextSelected = {
//                        //  PostOfficeAppRouter.navigateTo(Screen.TermsAndConditionsScreen)
//                    },
//                    onCheckedChange = {
//                    //    registerViewModel.onEvent(RegisterUIEvent.PrivacyPolicyCheckBoxClicked(it))
//                    }
//                )
//
//                Spacer(modifier = Modifier.height(40.dp))
//
//                ButtonComponent(
//                    value = stringResource(id = R.string.register),
//                    onButtonClicked = {
//                     //   registerViewModel.onEvent(RegisterUIEvent.RegisterButtonClicked)
//                    },
//                //    isEnabled = registerViewModel.allValidationsPassed.value
//                    isEnabled = true
//                )
//
//                Spacer(modifier = Modifier.height(20.dp))
//
//                DividerTextComponent()
//
//                ClickableLoginTextComponent(tryingToLogin = true, onTextSelected = {
//                    //AuthAppRouter.navigateTo(Screen.LoginScreen)
//                })
//            }
//        }
//    }
//
//}