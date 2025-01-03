package ie.setu.placemark.ui.screens.map

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import ie.setu.placemark.ui.theme.RunHunTheme
import ie.setu.placemark.ui.screens.report.ReportViewModel
import timber.log.Timber

@Composable
fun MapScreen(
    mapViewModel: MapViewModel = hiltViewModel(),
    reportViewModel: ReportViewModel = hiltViewModel(),

    ) {
    val uiSettings by remember { mutableStateOf(MapUiSettings(
        myLocationButtonEnabled = true,
        compassEnabled = true,
        mapToolbarEnabled = true
    )) }

    val properties by remember {
        mutableStateOf(MapProperties(
            mapType = MapType.NORMAL,
            isMyLocationEnabled = true
        ))
    }

    val runs = reportViewModel.uiRuns.collectAsState().value

    val currentLocation = mapViewModel.currentLatLng.collectAsState().value

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(currentLocation, 14f)
    }
    LaunchedEffect(currentLocation){
        mapViewModel.getLocationUpdates()
        cameraPositionState.animate(CameraUpdateFactory.newLatLng(currentLocation))
        cameraPositionState.position = CameraPosition.fromLatLngZoom(currentLocation, 14f)
    }

    Timber.i("MAP LAT/LNG COORDINATES $currentLocation ")

    Column{
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            uiSettings = uiSettings,
            properties = properties
        ) {
            Marker(
                state = MarkerState(position = currentLocation),
                title = "Current Location",
                snippet = "This is My Current Location"
            )
            runs.forEach {
                val position = LatLng(it.latitude,it.longitude)
                Marker(
                    state = MarkerState(position = position),
                    title = "${it.distanceAmount} ${it.unitType}",
                    snippet = it.message
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MapScreenPreview() {
    RunHunTheme {
        MapScreen( )
    }
}
