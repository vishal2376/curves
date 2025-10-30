package com.vishal2376.curves.demo.presentation.playground.text_animation

import androidx.compose.runtime.Composable
import com.vishal2376.curves.core.presentation.app.CurvesDemo
import com.vishal2376.curves.core.presentation.components.BaseCurvesScreen

object TextAnimationDemo : CurvesDemo {
	override val title: String
		get() = "Text Animation"
	override val route: String
		get() = "text_animation"

	@Composable
	override fun BaseScreen(onClickBack: () -> Unit) {
		BaseCurvesScreen(
			title = title,
			onClickBack = onClickBack
		) {
			TextAnimationScreen()
		}
	}
}