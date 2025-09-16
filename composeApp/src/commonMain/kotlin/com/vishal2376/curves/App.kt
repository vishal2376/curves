package com.vishal2376.curves

import androidx.compose.runtime.Composable
import com.vishal2376.curves.app.presentation.BeizerCurveScreen
import com.vishal2376.curves.core.presentation.theme.CurvesTheme

@Composable
fun App(darkTheme: Boolean, dynamicColor: Boolean) {
	CurvesTheme(darkTheme, dynamicColor) {
		BeizerCurveScreen()
	}
}