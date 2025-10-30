package com.vishal2376.curves.demo.presentation.playground.sequence_animation_experiment

import androidx.compose.runtime.Composable
import com.vishal2376.curves.core.presentation.app.CurvesDemo
import com.vishal2376.curves.core.presentation.components.BaseCurvesScreen

object SequenceAnimationExperimentDemo : CurvesDemo {
	override val title: String
		get() = "Sequence Animation Experiment"
	override val route: String
		get() = "sequence_animation_experiment"

	@Composable
	override fun BaseScreen(onClickBack: () -> Unit) {
		BaseCurvesScreen(
			title = title,
			onClickBack = onClickBack
		) {
			SequenceAnimationExperimentScreen()
		}
	}
}