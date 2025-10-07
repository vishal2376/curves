package com.vishal2376.curves.demo.presentation.playground.experiment_curves_3

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.dp
import com.vishal2376.curves.core.presentation.theme.ElectricAmber
import com.vishal2376.curves.core.presentation.theme.NeonYellow
import com.vishal2376.curves.core.presentation.theme.WarmOrange
import com.vishal2376.curves.demo.presentation.common.components.CustomLabel
import com.vishal2376.curves.demo.presentation.common.utils.polarLineTo
import com.vishal2376.curves.demo.presentation.common.utils.polarMoveTo
import kotlin.math.PI
import kotlin.math.sin

@Composable
fun ExperimentCurves3Screen() {

	val colors = MaterialTheme.colorScheme
	val resolution = 300
	var degree by remember {
		mutableStateOf(0f)
	}
	var distance by remember {
		mutableStateOf(0f)
	}
	val infiniteTransition = rememberInfiniteTransition(label = "pathLoop")

	val animatedValue by infiniteTransition.animateFloat(
		initialValue = 0f,
		targetValue = resolution.toFloat(),
		animationSpec = infiniteRepeatable(
			animation = tween(
				durationMillis = 4000,
				easing = LinearEasing
			),
			repeatMode = RepeatMode.Reverse
		),
		label = "animatedResolution"
	)

	val pulse by infiniteTransition.animateFloat(
		initialValue = 0.2f,
		targetValue = 0.6f,
		animationSpec = infiniteRepeatable(
			animation = tween(durationMillis = 1800, easing = LinearEasing),
			repeatMode = RepeatMode.Reverse
		),
		label = "glowPulse"
	)

	Column(
		modifier = Modifier
			.fillMaxSize()
			.background(colors.background)
			.padding(16.dp),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.SpaceBetween
	) {

		Box(
			modifier = Modifier
				.fillMaxWidth()
				.weight(1f),
			contentAlignment = Alignment.Center
		) {

			Canvas(modifier = Modifier) {
				val path = Path()
				val origin = center

				(0..animatedValue.toInt()).forEach { i ->
					val t = i / resolution.toFloat()
					degree = 360f * t
					distance = (400 * sin((degree * PI / 180) * 4)).toFloat()

					if (i == 0)
						path.polarMoveTo(degree, distance, origin)
					else
						path.polarLineTo(degree, distance, origin)
				}

				val glowColors = listOf(
					NeonYellow.copy(alpha = pulse),
					ElectricAmber.copy(alpha = pulse * 0.8f),
					WarmOrange.copy(alpha = pulse * 0.6f)
				)

				glowColors.forEachIndexed { index, color ->
					val strokeWidth = 12f + index * 10f
					drawPath(
						path = path,
						color = color,
						style = Stroke(width = strokeWidth)
					)
				}

				drawPath(
					path = path,
					brush = Brush.linearGradient(
						colors = listOf(WarmOrange, NeonYellow, ElectricAmber),
						start = androidx.compose.ui.geometry.Offset(0f, 0f),
						end = androidx.compose.ui.geometry.Offset(size.width, size.height)
					),
					style = Stroke(width = 4f)
				)

				drawIntoCanvas {
					repeat(3) { layer ->
						drawPath(
							path = path,
							color = NeonYellow.copy(alpha = 0.1f - layer * 0.02f),
							style = Stroke(width = 20f + layer * 8f)
						)
					}
				}
			}
		}

		Column(
			modifier = Modifier
				.fillMaxWidth()
				.background(colors.surface, RoundedCornerShape(24.dp))
				.padding(24.dp),
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			CustomLabel("Resolution", animatedValue.toInt())
			CustomLabel("Degree", degree.toInt())
			CustomLabel("Distance", distance.toInt())
		}
	}
}