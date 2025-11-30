package com.vishal2376.curves.demo.presentation.playground.fibonacci_sphere_animation

import androidx.compose.runtime.Composable
import com.vishal2376.curves.core.presentation.app.CurvesDemo
import com.vishal2376.curves.core.presentation.components.BaseCurvesScreen

object FibonacciSphereAnimationDemo : CurvesDemo {
	override val title: String
		get() = "Fibonacci Sphere Animation"
	override val route: String
		get() = "fibonacci_sphere_animation"

	@Composable
	override fun BaseScreen(onClickBack: () -> Unit) {
		BaseCurvesScreen(
			title = title,
			onClickBack = onClickBack
		) {
			FibonacciSphereAnimationScreen()
		}
	}
}