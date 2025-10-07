package com.vishal2376.curves.demo.presentation.playground.experiment_curves_3

import androidx.compose.runtime.Composable
import com.vishal2376.curves.core.presentation.app.CurvesDemo
import com.vishal2376.curves.core.presentation.components.BaseCurvesScreen

object ExperimentCurves3Demo : CurvesDemo {
	override val title: String
		get() = "Experiment Curves 3"
	override val route: String
		get() = "experiment_curves_3"

	@Composable
	override fun BaseScreen(onClickBack: () -> Unit) {
		BaseCurvesScreen(
			title = title,
			onClickBack = onClickBack
		) {
			ExperimentCurves3Screen()
		}
	}
}