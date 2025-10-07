package com.vishal2376.curves.demo.presentation.playground.experiment_curves

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
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.vishal2376.curves.demo.presentation.common.components.CustomLabel
import com.vishal2376.curves.demo.presentation.common.utils.polarLineTo
import com.vishal2376.curves.demo.presentation.common.utils.polarMoveTo

@Composable
fun ExperimentCurvesScreen() {

	val colors = MaterialTheme.colorScheme

	val res = 30
	var degree by remember {
		mutableStateOf(0f)
	}
	var distance by remember {
		mutableStateOf(0f)
	}
	val resAnim by rememberInfiniteTransition().animateFloat(
		initialValue = 2f,
		targetValue = res.toFloat(),
		animationSpec = infiniteRepeatable(
			animation = tween(
				durationMillis = 3000,
				easing = LinearEasing
			),
			repeatMode = RepeatMode.Reverse
		),
		label = ""
	)


	Column(
		modifier = Modifier.fillMaxSize()
			.background(colors.background)
			.padding(16.dp),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.SpaceBetween
	) {

		Box(
			modifier = Modifier.fillMaxWidth().weight(1f),
			contentAlignment = Alignment.Center
		) {

			Canvas(
				modifier = Modifier
			) {
				val distanceConst = 300f

				val path = Path()
				path.polarMoveTo(0f, distanceConst)
				(0..res.toInt()).forEach { i ->
					val t = i / resAnim
					degree = t * 360f
					distance = distanceConst
					path.apply {
						polarLineTo(degree, distance)
					}
				}

				drawPath(
					path = path,
					color = colors.primary,
					style = Stroke(width = 10f)
				)
			}
		}
		Column(
			modifier = Modifier.fillMaxWidth()
				.background(colors.surface, RoundedCornerShape(24.dp))
				.padding(24.dp),
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			CustomLabel("Resolution", resAnim.toInt())
			CustomLabel("Degree", degree.toInt())
			CustomLabel("Distance", distance.toInt())
		}
	}
}
