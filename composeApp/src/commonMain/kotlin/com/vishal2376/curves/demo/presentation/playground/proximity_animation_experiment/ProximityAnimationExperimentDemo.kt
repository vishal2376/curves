package com.vishal2376.curves.demo.presentation.playground.proximity_animation_experiment

import androidx.compose.runtime.Composable
import com.vishal2376.curves.core.presentation.app.CurvesDemo
import com.vishal2376.curves.core.presentation.components.BaseCurvesScreen

object ProximityAnimationExperimentDemo : CurvesDemo {
	override val title: String
		get() = "Proximity Animation (Experiment)"
	override val route: String
		get() = "proximity_animation_experiment"

	@Composable
	override fun BaseScreen(onClickBack: () -> Unit) {
		BaseCurvesScreen(
			title = title,
			onClickBack = onClickBack
		) {
			ProximityAnimationExperimentScreen()
		}
	}
}