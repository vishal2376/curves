package com.vishal2376.curves.core.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
actual fun CurvesTheme(
	darkTheme: Boolean,
	dynamicColor: Boolean,
	content: @Composable (() -> Unit),
) {
	MaterialTheme(
		colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
		content = content
	)
}