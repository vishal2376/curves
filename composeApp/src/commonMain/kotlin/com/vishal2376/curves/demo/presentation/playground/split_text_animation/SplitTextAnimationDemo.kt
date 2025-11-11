package com.vishal2376.curves.demo.presentation.playground.split_text_animation

import androidx.compose.runtime.Composable
import com.vishal2376.curves.core.presentation.app.CurvesDemo
import com.vishal2376.curves.core.presentation.components.BaseCurvesScreen

object SplitTextAnimationDemo : CurvesDemo {
	override val title: String
		get() = "Split Text Animation"
	override val route: String
		get() = "split_text_animation"

	@Composable
	override fun BaseScreen(onClickBack: () -> Unit) {
		BaseCurvesScreen(
			title = title,
			onClickBack = onClickBack
		) {
			SplitTextAnimationScreen()
		}
	}
}