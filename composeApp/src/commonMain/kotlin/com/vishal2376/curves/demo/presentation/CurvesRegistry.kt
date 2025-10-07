package com.vishal2376.curves.demo.presentation

import com.vishal2376.curves.core.presentation.app.CurvesDemo
import com.vishal2376.curves.demo.presentation.playground.beizer_curves.BeizerCurvesDemo

object CurvesRegistry {
	val demos: List<CurvesDemo> = listOf(
		BeizerCurvesDemo,
	)
}