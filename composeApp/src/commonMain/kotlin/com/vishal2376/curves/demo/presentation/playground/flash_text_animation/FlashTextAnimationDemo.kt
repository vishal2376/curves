package com.vishal2376.curves.demo.presentation.playground.flash_text_animation

import androidx.compose.runtime.Composable
import com.vishal2376.curves.core.presentation.app.CurvesDemo
import com.vishal2376.curves.core.presentation.components.BaseCurvesScreen

object FlashTextAnimationDemo : CurvesDemo {
	override val title: String
		get() = "Flash Text Animation"
	override val route: String
		get() = "flash_text_animation"

	@Composable
	override fun BaseScreen(onClickBack: () -> Unit) {
		BaseCurvesScreen(
			title = title,
			onClickBack = onClickBack
		) {
		}
			FlashTextAnimationScreen()
	}
}