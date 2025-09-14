package com.vishal2376.curves

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() {
	application {
		Window(
			onCloseRequest = ::exitApplication,
			title = "KMP Curves",
		) {
			App(darkTheme = isSystemInDarkTheme(), dynamicColor = false)
		}
	}
}