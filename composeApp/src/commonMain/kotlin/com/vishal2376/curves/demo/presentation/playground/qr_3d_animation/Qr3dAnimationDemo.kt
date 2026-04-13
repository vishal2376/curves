package com.vishal2376.curves.demo.presentation.playground.qr_3d_animation

import androidx.compose.runtime.Composable
import com.vishal2376.curves.core.presentation.app.CurvesDemo
import com.vishal2376.curves.core.presentation.components.BaseCurvesScreen

object Qr3dAnimationDemo : CurvesDemo {
	override val title: String
		get() = "QR Code Animation"
	override val route: String
		get() = "qr_3d_animation"

	@Composable
	override fun BaseScreen(onClickBack: () -> Unit) {
		BaseCurvesScreen(
			title = title,
			onClickBack = onClickBack
		) {
			Qr3dAnimationScreen()
		}
	}
}