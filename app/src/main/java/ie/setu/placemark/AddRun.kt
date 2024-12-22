//package ie.setu.placemark
//
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.activity.enableEdgeToEdge
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.width
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Add
//import androidx.compose.material.icons.filled.Edit
//import androidx.compose.material.icons.filled.Menu
//import androidx.compose.material.icons.filled.Warning
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Icon
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.OutlinedTextFieldDefaults
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.material3.TopAppBar
//import androidx.compose.material3.TopAppBarDefaults
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import com.github.ajalt.timberkt.Timber
//import ie.setu.placemark.models.RunModel
//import ie.setu.placemark.ui.theme.RunHunTheme
//import timber.log.Timber.i
//
//class MainActivity : ComponentActivity() {
//    @OptIn(ExperimentalMaterial3Api::class)
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        Timber.plant(Timber.DebugTree())
//        i("RunHun MainActivity started..")
//        setContent {
//////            RunHunTheme {
//////                Scaffold(
//////                    modifier = Modifier.fillMaxSize(),
//////                    topBar = {
//////                        TopAppBar(
//////                            title = {
//////                                Text(
//////                                    text = stringResource(id = R.string.app_name),
//////                                    color = Color.White
//////                                )
//////                            },
//////                            colors = TopAppBarDefaults.largeTopAppBarColors(
//////                                containerColor = MaterialTheme.colorScheme.primary
//////                            ),
//////                            navigationIcon = {
//////                                Icon(imageVector = Icons.Filled.Menu,
//////                                    contentDescription = "Menu",
//////                                    tint = Color.White)
//////                            }
//////                        )
//////                    }
//////                )
//////                { innerPadding ->
//////                    AddRun(
//////                        modifier = Modifier.padding(innerPadding)
//////                    )
//////                }
//////            }
//////        }
//    }
//}
//
//fun addRun(title: String) {
//    val run = RunModel()
//
//    run.title = title
//    i("Title Entered is : ${run.title}")
//}
//
//
//@Composable
//fun AddRun(modifier: Modifier = Modifier) {
//    var title by remember { mutableStateOf("") }
//    var showError by remember { mutableStateOf(false) }
//    Column(
//        modifier = modifier
//            .fillMaxSize()
//            .padding(all = 8.dp),
//        verticalArrangement = Arrangement.spacedBy(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        OutlinedTextField(
//            colors = OutlinedTextFieldDefaults.colors(
//                focusedBorderColor = MaterialTheme.colorScheme.primary,
//                unfocusedBorderColor = MaterialTheme.colorScheme.primary,
//                cursorColor = MaterialTheme.colorScheme.primary
//            ),
//            value = title,
//            onValueChange = {
//                title = it
//                showError = false
//            },
//            isError = showError,
//            modifier = modifier.fillMaxWidth(),
//            label = { Text(stringResource(id = R.string.text_titleHint)) },
//            trailingIcon = {
//                if (showError)
//                    Icon(
//                        Icons.Filled.Warning, "error",
//                        tint = MaterialTheme.colorScheme.error
//                    )
//                else
//                    Icon(
//                        Icons.Default.Edit, contentDescription = "add/edit",
//                        tint = Color.Black
//                    )
//            } ,
//            supportingText = { ShowSupportText(showError) }
//        )
//        Button(
//            onClick = {
//                if (title.isEmpty()) {
//                    showError = true
//                } else {
//                    addRun(title)
//                }
//            },
//            modifier = Modifier.align(Alignment.CenterHorizontally),
//            elevation = ButtonDefaults.buttonElevation(20.dp)
//        ) {
//            Icon(Icons.Default.Add, contentDescription = "Add")
//            Spacer(modifier = Modifier.width(width = 4.dp))
//            Text(stringResource(id = R.string.button_addRun))
//        }
//    }}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Preview(showBackground = true)
//@Composable
//fun AddRunPreview() {
//    RunHunTheme {
//        Scaffold(
//            modifier = Modifier.fillMaxSize(),
//            topBar = {
//                TopAppBar(
//                    title = {
//                        Text(
//                            text = stringResource(id = R.string.app_name),
//                            color = Color.White
//                        )
//                    },
//                    colors = TopAppBarDefaults.largeTopAppBarColors(
//                        containerColor = MaterialTheme.colorScheme.primary
//                    ),
//                )
//            },
//
//            )
//        { innerPadding ->
//            AddRun(
//                modifier = Modifier.padding(innerPadding)
//            )
//        }
//    }
//}
//
//@Composable
//fun ShowSupportText(isError : Boolean)
//{
//    if (isError)
//        Text(
//            text = "This Field is Required",
//            style = MaterialTheme.typography.labelMedium,
//            color = MaterialTheme.colorScheme.error,
//        )
//    else Text(text = "")
//}