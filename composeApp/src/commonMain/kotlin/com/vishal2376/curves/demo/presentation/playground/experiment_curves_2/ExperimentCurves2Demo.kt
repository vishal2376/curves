package com.vishal2376.curves.demo.presentation.playground.experiment_curves_2

import androidx.compose.runtime.Composable
import com.vishal2376.curves.core.presentation.app.CurvesDemo
import com.vishal2376.curves.core.presentation.components.BaseCurvesScreen

object ExperimentCurves2Demo : CurvesDemo {
	override val title: String
		get() = "Experiment Curves 2"
	override val route: String
		get() = "experiment_curves_2"

	@Composable
	override fun BaseScreen(onClickBack: () -> Unit) {
		BaseCurvesScreen(
			title = title,
			onClickBack = onClickBack
		) {
			ExperimentCurves2Screen()
		}
	}
}