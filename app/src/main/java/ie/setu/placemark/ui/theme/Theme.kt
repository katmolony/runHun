package ie.setu.placemark.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.darkColorScheme


private val DarkColorScheme = darkColorScheme(
    primary = LightPink80,
    secondary = LavenderBlush80,
    tertiary = DeepPink80
)

private val LightColorScheme = lightColorScheme(
    primary = HotPink40,
    secondary = PaleVioletRed40,
    tertiary = Fuchsia40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

val startGradientColor = Color(0xFF1e88e5)
val endGradientColor = Color(0xFF005cb2)

val gStartGradientColor = Color(0xFF013B6E)
val gEndGradientColor = Color(0xFF2189EB)

@Composable
fun RunHunTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
//        shapes = Shapes,
        content = content
    )
}