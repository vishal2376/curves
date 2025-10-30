package com.vishal2376.curves.demo.presentation.playground.spiral_animation

import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.vishal2376.curves.core.presentation.theme.CurvesTheme
import com.vishal2376.curves.demo.presentation.common.utils.polarLineTo
import com.vishal2376.curves.demo.presentation.common.utils.polarMoveTo
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SpiralAnimationScreen() {

	val colors = MaterialTheme.colorScheme

	val infiniteAnimation = rememberInfiniteTransition("spiral")
	val progress by infiniteAnimation.animateFloat(
		initialValue = 0f,
		targetValue = 1f,
		animationSpec = InfiniteRepeatableSpec(
			repeatMode = RepeatMode.Reverse,
			animation = tween(durationMillis = 3000, easing = EaseInOut)
		)
	)

	Column(
		modifier = Modifier.fillMaxSize()
			.background(colors.background)
			.padding(16.dp),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center
	) {
		val resolution = 500
		val loops = 5

		Canvas(
			modifier = Modifier.aspectRatio(1f)
		) {
			val path = Path()
			(0..resolution).forEach { i ->
				val t = (i / resolution.toFloat()) * progress
				val degree = (loops * 360f) * t

				val distance = lerp(
					start = 0f,
					stop = size.minDimension / 2,
					fraction = t
				)

				if (i == 0) {
					path.polarMoveTo(0f, 0f, center)
				} else {
					path.polarLineTo(degree, distance, origin = center)
				}

			}

			drawPath(
				path = path,
				color = colors.primary,
				style = Stroke(width = 10f)
			)
		}
	}
}


@Preview
@Composable
fun SpiralAnimationScreenPreview() {
	CurvesTheme(darkTheme = true, dynamicColor = false) {
		SpiralAnimationScreen()
	}
}
