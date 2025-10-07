package com.vishal2376.curves.demo.presentation.playground.experiment_curves

import androidx.compose.runtime.Composable
import com.vishal2376.curves.core.presentation.app.CurvesDemo
import com.vishal2376.curves.core.presentation.components.BaseCurvesScreen

object ExperimentCurvesDemo : CurvesDemo {
	override val title: String
		get() = "Experiment Curves"
	override val route: String
		get() = "experiment_curves"

	@Composable
	override fun BaseScreen(onClickBack: () -> Unit) {
		BaseCurvesScreen(
			title = title,
			onClickBack = onClickBack
		) {
			ExperimentCurvesScreen()
		}
	}
}