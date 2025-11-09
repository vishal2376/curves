package com.vishal2376.curves

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

fun main() {
	application {
		Window(
			onCloseRequest = ::exitApplication,
			title = "KMP Curves",
			state = rememberWindowState(
				size = DpSize(1280.dp, 920.dp),
				position = WindowPosition(Alignment.Center)
			)
		) {
			App(darkTheme = isSystemInDarkTheme(), dynamicColor = false)
		}
	}
}