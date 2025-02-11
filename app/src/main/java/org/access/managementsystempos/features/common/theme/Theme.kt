package org.access.managementsystempos.features.common.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val AccessBlue = Color(0xFF1B263B)
private val LightBackground = Color(0xFFF0F0F0) // Light gray for readability
private val DarkBackground = Color(0xFF121212)  // Dark gray for dark mode
private val LightOnBackground = Color(0xFF000000) // Dark text for light mode
private val DarkOnBackground = Color(0xFFFFFFFF) // Light text for dark mode

private val DarkColorScheme = darkColorScheme(
    primary = AccessBlue,
    onPrimary = Color.White,
    primaryContainer = AccessBlue,
    onPrimaryContainer = Color.White,
    background = DarkBackground,
    onBackground = DarkOnBackground,
    surface = AccessBlue,
    onSurface = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = AccessBlue,
    onPrimary = Color.White,
    primaryContainer = AccessBlue,
    onPrimaryContainer = Color.White,
    background = LightBackground,
    onBackground = LightOnBackground,
    surface = Color.White,
    onSurface = Color.Black
)

@Composable
fun ACCESSManagementSystemPOSTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}


//    /* Other default colors to override
//    background = Color(0xFFFFFBFE),
//    surface = Color(0xFFFFFBFE),
//    onPrimary = Color.White,
//    onSecondary = Color.White,
//    onTertiary = Color.White,
//    onBackground = Color(0xFF1C1B1F),
//    onSurface = Color(0xFF1C1B1F),
//    */
//)
