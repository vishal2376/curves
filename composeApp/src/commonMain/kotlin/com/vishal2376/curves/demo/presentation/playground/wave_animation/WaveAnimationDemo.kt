package com.vishal2376.curves.demo.presentation.playground.wave_animation

import androidx.compose.runtime.Composable
import com.vishal2376.curves.core.presentation.app.CurvesDemo
import com.vishal2376.curves.core.presentation.components.BaseCurvesScreen

object WaveAnimationDemo : CurvesDemo {
	override val title: String
		get() = "Wave Animation"
	override val route: String
		get() = "wave_animation"

	@Composable
	override fun BaseScreen(onClickBack: () -> Unit) {
		BaseCurvesScreen(
			title = title,
			onClickBack = onClickBack
		) {
			WaveAnimationScreen()
		}
	}
}