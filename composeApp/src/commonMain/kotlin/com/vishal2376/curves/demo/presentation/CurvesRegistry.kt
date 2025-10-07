package com.vishal2376.curves.demo.presentation

import com.vishal2376.curves.core.presentation.app.CurvesDemo
import com.vishal2376.curves.demo.presentation.playground.beizer_curves.BeizerCurvesDemo
import com.vishal2376.curves.demo.presentation.playground.experiment_curves.ExperimentCurvesDemo
import com.vishal2376.curves.demo.presentation.playground.experiment_curves_2.ExperimentCurves2Demo
import com.vishal2376.curves.demo.presentation.playground.experiment_curves_3.ExperimentCurves3Demo

object CurvesRegistry {
	val demos: List<CurvesDemo> = listOf(
		BeizerCurvesDemo,
		ExperimentCurvesDemo,
		ExperimentCurves2Demo,
		ExperimentCurves3Demo,
	)
}