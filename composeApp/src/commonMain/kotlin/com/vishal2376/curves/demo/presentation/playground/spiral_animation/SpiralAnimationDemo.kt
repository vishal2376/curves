package com.vishal2376.curves.demo.presentation.playground.spiral_animation

import androidx.compose.runtime.Composable
import com.vishal2376.curves.core.presentation.app.CurvesDemo
import com.vishal2376.curves.core.presentation.components.BaseCurvesScreen

object SpiralAnimationDemo : CurvesDemo {
	override val title: String
		get() = "Spiral Animation"
	override val route: String
		get() = "spiral_animation"

	@Composable
	override fun BaseScreen(onClickBack: () -> Unit) {
		BaseCurvesScreen(
			title = title,
			onClickBack = onClickBack
		) {
			SpiralAnimationScreen()
		}
	}
}