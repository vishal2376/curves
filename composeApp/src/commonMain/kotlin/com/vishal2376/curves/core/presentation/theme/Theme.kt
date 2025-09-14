package com.vishal2376.curves.core.presentation.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

val DarkColorScheme = darkColorScheme(
	primary = MochaLavender,
	onPrimary = MochaSurface,
	secondary = MochaPink,
	onSecondary = MochaSurface,
	background = MochaBase,
	onBackground = MochaText,
	surface = MochaSurface,
	onSurface = MochaText,
	primaryContainer = MochaOverlay,
	onPrimaryContainer = MochaText,
	secondaryContainer = MochaOverlay,
	onSecondaryContainer = MochaSubtext,
	tertiary = MochaBlue,
	onTertiary = MochaSurface
)


val LightColorScheme = lightColorScheme(
	primary = LatteLavender,
	onPrimary = LatteBase,
	secondary = LattePink,
	onSecondary = LatteBase,
	background = LatteBase,
	onBackground = LatteText,
	surface = LatteSurface,
	onSurface = LatteText,
	primaryContainer = LatteOverlay,
	onPrimaryContainer = LatteText,
	secondaryContainer = LatteOverlay,
	onSecondaryContainer = LatteSubtext,
	tertiary = LatteBlue,
	onTertiary = LatteBase
)

@Composable
expect fun CurvesTheme(
	darkTheme: Boolean,
	dynamicColor: Boolean,
	content: @Composable () -> Unit,
)