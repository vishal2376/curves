package com.vishal2376.curves.demo.presentation.playground.sci_fi_door_animation

import androidx.compose.runtime.Composable
import com.vishal2376.curves.core.presentation.app.CurvesDemo
import com.vishal2376.curves.core.presentation.components.BaseCurvesScreen

object SciFiDoorAnimationDemo : CurvesDemo {
	override val title: String
		get() = "Sci-fi Door Animation"
	override val route: String
		get() = "sci_fi_door_animation"

	@Composable
	override fun BaseScreen(onClickBack: () -> Unit) {
		BaseCurvesScreen(
			title = title,
			onClickBack = onClickBack
		) {
			SciFiDoorAnimationScreen()
		}
	}
}