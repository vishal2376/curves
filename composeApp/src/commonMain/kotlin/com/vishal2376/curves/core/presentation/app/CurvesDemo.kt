package com.vishal2376.curves.core.presentation.app

import androidx.compose.runtime.Composable

interface CurvesDemo {
	val title: String
	val route: String

	@Composable
	fun BaseScreen(onClickBack: () -> Unit)
}