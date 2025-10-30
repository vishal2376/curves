package com.vishal2376.curves.demo.presentation.playground.ribbon_animation

import androidx.compose.runtime.Composable
import com.vishal2376.curves.core.presentation.app.CurvesDemo
import com.vishal2376.curves.core.presentation.components.BaseCurvesScreen

object RibbonAnimationDemo : CurvesDemo {
	override val title: String
		get() = "Ribbon Animation"
	override val route: String
		get() = "ribbon_animation"

	@Composable
	override fun BaseScreen(onClickBack: () -> Unit) {
		BaseCurvesScreen(
			title = title,
			onClickBack = onClickBack
		) {
			RibbonAnimationScreen()
		}
	}
}