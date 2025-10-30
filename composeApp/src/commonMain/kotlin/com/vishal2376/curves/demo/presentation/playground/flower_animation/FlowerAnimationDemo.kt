package com.vishal2376.curves.demo.presentation.playground.flower_animation

import androidx.compose.runtime.Composable
import com.vishal2376.curves.core.presentation.app.CurvesDemo
import com.vishal2376.curves.core.presentation.components.BaseCurvesScreen

object FlowerAnimationDemo : CurvesDemo {
	override val title: String
		get() = "Flower Animation"
	override val route: String
		get() = "flower_animation"

	@Composable
	override fun BaseScreen(onClickBack: () -> Unit) {
		BaseCurvesScreen(
			title = title,
			onClickBack = onClickBack
		) {
			FlowerAnimationScreen()
		}
	}
}