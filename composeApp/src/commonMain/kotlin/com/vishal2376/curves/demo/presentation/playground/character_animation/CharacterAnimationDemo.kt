package com.vishal2376.curves.demo.presentation.playground.character_animation

import androidx.compose.runtime.Composable
import com.vishal2376.curves.core.presentation.app.CurvesDemo
import com.vishal2376.curves.core.presentation.components.BaseCurvesScreen

object CharacterAnimationDemo : CurvesDemo {
	override val title: String
		get() = "Character Animation"
	override val route: String
		get() = "character_animation"

	@Composable
	override fun BaseScreen(onClickBack: () -> Unit) {
		BaseCurvesScreen(
			title = title,
			onClickBack = onClickBack
		) {
		}
			CharacterAnimationScreen()
	}
}