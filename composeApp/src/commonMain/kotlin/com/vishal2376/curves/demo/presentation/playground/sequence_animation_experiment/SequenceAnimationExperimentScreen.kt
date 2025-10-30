package com.vishal2376.curves.demo.presentation.playground.sequence_animation_experiment

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp
import com.vishal2376.curves.core.presentation.theme.CurvesTheme
import com.vishal2376.curves.demo.presentation.common.utils.PolarPoint
import com.vishal2376.curves.demo.presentation.common.utils.polarGrid
import com.vishal2376.curves.demo.presentation.common.utils.polarLine
import com.vishal2376.curves.demo.presentation.common.utils.polarPoint
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SequenceAnimationExperimentScreen() {
	val colors = MaterialTheme.colorScheme

	val gridProgress = remember { Animatable(0f) }
	val point1Progress = remember { Animatable(0f) }
	val point2Progress = remember { Animatable(0f) }
	val point3Progress = remember { Animatable(0f) }
	val lineProgress = remember { Animatable(0f) }
	val rotateProgress = remember { Animatable(0f) }

	LaunchedEffect(Unit) {
		gridProgress.animateTo(
			targetValue = 1f,
			animationSpec = tween(durationMillis = 800)
		)

		point1Progress.animateTo(
			targetValue = 1f,
			animationSpec = tween(durationMillis = 1000)
		)

		point2Progress.animateTo(
			targetValue = 1f,
			animationSpec = tween(durationMillis = 1000)
		)

		point3Progress.animateTo(
			targetValue = 1f,
			animationSpec = tween(durationMillis = 1000)
		)


		lineProgress.animateTo(
			targetValue = 1f,
			animationSpec = tween(durationMillis = 1000)
		)

		rotateProgress.animateTo(
			targetValue = 1f,
			animationSpec = tween(durationMillis = 1000)
		)
	}

	Canvas(
		modifier = Modifier
			.fillMaxSize()
			.background(colors.background)
			.padding(16.dp)
	) {
		polarGrid(row = 20, col = 12, progress = gridProgress.value)

		rotate(degrees = rotateProgress.value * 90f) {
			
			val pointA = polarPoint(PolarPoint(), progress = point1Progress.value)
			val pointB = polarPoint(PolarPoint(60f, 500f), progress = point2Progress.value)
			val pointC = polarPoint(PolarPoint(180f, 500f), progress = point3Progress.value)

			polarLine(pointA, pointB, width = 10f, progress = lineProgress.value)
			polarLine(pointA, pointC, width = 10f, progress = lineProgress.value)
		}
	}
}

@Preview
@Composable
private fun SequenceAnimationExperimentScreenPreview() {
	CurvesTheme(darkTheme = true, dynamicColor = false) {
		SequenceAnimationExperimentScreen()
	}
}