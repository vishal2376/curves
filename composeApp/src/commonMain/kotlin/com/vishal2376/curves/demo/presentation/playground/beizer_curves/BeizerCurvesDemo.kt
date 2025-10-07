package com.vishal2376.curves.demo.presentation.playground.beizer_curves

import androidx.compose.runtime.Composable
import com.vishal2376.curves.core.presentation.app.CurvesDemo
import com.vishal2376.curves.core.presentation.components.BaseCurvesScreen

object BeizerCurvesDemo : CurvesDemo {
	override val title: String
		get() = "Beizer Curves"
	override val route: String
		get() = "beizer_curves"

	@Composable
	override fun BaseScreen(onClickBack: () -> Unit) {
		BaseCurvesScreen(
			title = title,
			onClickBack = onClickBack
		) { BeizerCurveScreen() }
	}
}