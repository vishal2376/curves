package com.vishal2376.curves.demo.presentation.playground.wave_animation

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.vishal2376.curves.core.presentation.theme.CurvesTheme
import com.vishal2376.curves.core.presentation.theme.SoftBlue
import com.vishal2376.curves.core.presentation.theme.SoftRed
import com.vishal2376.curves.demo.presentation.common.utils.polarLineTo
import com.vishal2376.curves.demo.presentation.common.utils.polarMoveTo
import com.vishal2376.curves.demo.presentation.common.utils.toRadians
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.sin

@Composable
fun WaveAnimationScreen() {

	val colors = MaterialTheme.colorScheme

	var length by remember { mutableStateOf(0.8f) }

	val infiniteTransition = rememberInfiniteTransition(label = "wave motion")
	val phase by infiniteTransition.animateFloat(
		initialValue = 0f,
		targetValue = 360f,
		animationSpec = infiniteRepeatable(
			animation = tween(durationMillis = 2000, easing = LinearEasing),
			repeatMode = RepeatMode.Restart
		),
		label = "phase"
	)

	Box(
		modifier = Modifier
			.fillMaxSize()
			.background(colors.background)
			.padding(16.dp),
		contentAlignment = Alignment.Center
	) {

		val resolution = 500
		val radius = 200f

		Canvas(modifier = Modifier.aspectRatio(1f)) {

			val wavePath = wavePath(resolution, radius, phase, center)
			val measure = PathMeasure()
			measure.setPath(wavePath, true)
			val len = measure.length

			drawPath(
				path = wavePath,
				brush = Brush.sweepGradient(
					colors = listOf(SoftBlue, SoftRed, SoftBlue)
				),
				style = Stroke(
					width = 10f,
					pathEffect = PathEffect.dashPathEffect(
						floatArrayOf(len * length, len),
						0f
					)
				)
			)

			drawCircle(SoftRed, radius * phase / 360f, style = Stroke(width = 10f))
		}
	}
}


private fun wavePath(resolution: Int, radius: Float, phase: Float, origin: Offset): Path {
	val wavePath = Path()

	val amplitude = 50f
	val frequency = 6
	val newRadius = radius + 150f


	(0..resolution).forEach { i ->
		val t = i / resolution.toFloat()

		val degree = (360f * t) + phase
		val wave = amplitude * sin((degree * frequency).toRadians())
		val distance = newRadius + wave

		if (t == 0f) {
			wavePath.polarMoveTo(phase, distance, origin)
		}

		wavePath.polarLineTo(degree, distance, origin)
	}
	wavePath.close()

	return wavePath
}

@Preview
@Composable
private fun WaveAnimationScreenPreview() {
	CurvesTheme(darkTheme = true, dynamicColor = false) {
		WaveAnimationScreen()
	}
}