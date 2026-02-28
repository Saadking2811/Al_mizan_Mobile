package dz.gov.almizan.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = AlMizanColors.GoldAlMizan,
    onPrimary = AlMizanColors.NavySovereign,
    primaryContainer = AlMizanColors.BlueRoyal,
    onPrimaryContainer = AlMizanColors.GoldAlMizan,
    
    secondary = AlMizanPastelColors.BluePrincipal,
    onSecondary = AlMizanColors.White,
    secondaryContainer = AlMizanPastelColors.GrisBleu,
    onSecondaryContainer = AlMizanColors.White,
    
    tertiary = AlMizanPastelColors.BeigeDore,
    onTertiary = AlMizanColors.NavySovereign,
    
    background = AlMizanColors.NavyDeep,
    onBackground = AlMizanColors.Gray100,
    
    surface = AlMizanColors.BlueMidnight,
    onSurface = AlMizanColors.White,
    surfaceVariant = AlMizanColors.BlueRoyal,
    onSurfaceVariant = AlMizanColors.Gray200,
    
    error = AlMizanColors.ErrorLight,
    onError = AlMizanColors.White,
    
    outline = AlMizanColors.Gray600,
    outlineVariant = AlMizanColors.Gray700
)

private val LightColorScheme = lightColorScheme(
    primary = AlMizanColors.NavySovereign,
    onPrimary = AlMizanColors.White,
    primaryContainer = AlMizanColors.BlueRoyal,
    onPrimaryContainer = AlMizanColors.White,
    
    secondary = AlMizanPastelColors.BluePrincipal,
    onSecondary = AlMizanColors.White,
    secondaryContainer = AlMizanPastelColors.BlueClair,
    onSecondaryContainer = AlMizanPastelColors.TextPrimary,
    
    tertiary = AlMizanColors.GoldAlMizan,
    onTertiary = AlMizanColors.NavySovereign,
    tertiaryContainer = AlMizanPastelColors.BeigeDore,
    onTertiaryContainer = AlMizanPastelColors.TextPrimary,
    
    background = AlMizanPastelColors.Background,
    onBackground = AlMizanPastelColors.TextPrimary,
    
    surface = AlMizanColors.White,
    onSurface = AlMizanPastelColors.TextPrimary,
    surfaceVariant = AlMizanPastelColors.SurfaceVariant,
    onSurfaceVariant = AlMizanPastelColors.TextSecondary,
    
    error = AlMizanColors.Error,
    onError = AlMizanColors.White,
    errorContainer = AlMizanPastelColors.ErrorPastel,
    onErrorContainer = AlMizanColors.Error,
    
    outline = AlMizanPastelColors.Border,
    outlineVariant = AlMizanPastelColors.TextDisabled
)

@Composable
fun AlMizanTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AlMizanTypography,
        content = content
    )
}
