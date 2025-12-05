package com.vishal2376.curves.demo.presentation.playground.illusion_animation

import androidx.compose.runtime.Composable
import com.vishal2376.curves.core.presentation.app.CurvesDemo
import com.vishal2376.curves.core.presentation.components.BaseCurvesScreen

object IllusionAnimationDemo : CurvesDemo {
	override val title: String
		get() = "Illusion Animation"
	override val route: String
		get() = "illusion_animation"

	@Composable
	override fun BaseScreen(onClickBack: () -> Unit) {
		BaseCurvesScreen(
			title = title,
			onClickBack = onClickBack
		) {
			IllusionAnimationScreen()
		}
	}
}