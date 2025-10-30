package com.vishal2376.curves.demo.presentation.playground.ribbon_animation

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.lerp
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import com.vishal2376.curves.demo.presentation.common.components.CoolSlider
import com.vishal2376.curves.demo.presentation.common.utils.polarLineTo
import com.vishal2376.curves.demo.presentation.common.utils.polarMoveTo
import kotlin.math.max
import kotlin.math.min

@Composable
fun RibbonAnimationScreen() {

	val colors = MaterialTheme.colorScheme

	val progress = rememberInfiniteTransition().animateFloat(
		initialValue = 0f,
		targetValue = 1f,
		animationSpec = infiniteRepeatable(
			animation = tween(
				durationMillis = 3000,
				easing = LinearEasing
			),
			repeatMode = RepeatMode.Reverse
		),
		label = "animatedResolution"
	)

	var loops by remember {
		mutableStateOf(7)
	}

	Column(
		modifier = Modifier.fillMaxSize()
			.background(colors.background)
			.padding(16.dp),
		horizontalAlignment = Alignment.CenterHorizontally
	) {

		Column(
			modifier = Modifier.weight(1f),
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.spacedBy(100.dp, Alignment.CenterVertically)
		) {
			Box(
				modifier = Modifier.fillMaxWidth(.8f)
					.ribbon(
						brush = Brush.verticalGradient(listOf(colors.primary, colors.error)),
						loops = loops,
					)
					.background(colors.surface, RoundedCornerShape(24.dp))
					.border(1.dp, colors.primary, RoundedCornerShape(24.dp))
					.padding(32.dp, 24.dp),
				contentAlignment = Alignment.Center
			) {
				Text("Custom Button", fontSize = 20.sp, color = colors.primary)
			}
			Box(
				modifier = Modifier.fillMaxWidth(.8f)
					.ribbon(
						brush = Brush.verticalGradient(listOf(colors.primary, colors.error)),
						loops = loops,
						progress = progress.value
					)
					.background(colors.surface, RoundedCornerShape(24.dp))
					.border(1.dp, colors.primary, RoundedCornerShape(24.dp))
					.padding(32.dp, 24.dp),
				contentAlignment = Alignment.Center
			) {
				Text("Custom Button", fontSize = 20.sp, color = colors.primary)
			}
		}

		Column(
			modifier = Modifier.fillMaxWidth()
				.background(colors.surface, RoundedCornerShape(24.dp))
				.padding(24.dp),
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			CoolSlider(
				value = loops / 10f,
				onValueChange = { loops = max(1f, (it * 10)).toInt() },
				label = "loops",
				color = colors.primary,
			)
		}
	}
}

private fun Modifier.ribbon(
	brush: Brush,
	stroke: Dp = 6.dp,
	loops: Int = 5,
	progress: Float = 1f,
): Modifier {
	return drawWithCache {
		val loops = loops - .5f
		val ribbonPath = createRibbon(
			start = Offset(0f, size.height * .5f),
			end = Offset(size.width, size.height * .5f),
			radius = (size.height * .5f) + stroke.toPx(),
			startAngle = -90f,
			loops = loops,
		)

		val measure = PathMeasure()
		measure.setPath(ribbonPath, false)

		var isPositive = measure.getTangent(0f).y > 0f
		val distanceArray = mutableListOf<Float>()
		var segmentStartDistance = 0f

		val resolution = 1000
		for (i in 0..resolution) {
			val t = i / resolution.toFloat()
			val distance = t * measure.length
			val tan = measure.getTangent(distance)
			val currentIsPositive = tan.y > 0f

			if (currentIsPositive != isPositive) {
				val segmentLength = distance - segmentStartDistance
				distanceArray.add(segmentLength)

				segmentStartDistance = distance
				isPositive = currentIsPositive
			} else if (i == resolution) {
				val segmentLength = distance - segmentStartDistance
				distanceArray.add(segmentLength)
			}
		}

		onDrawWithContent {
			if (progress > 0f)
				drawPath(
					path = ribbonPath,
					brush = brush,
					style = Stroke(
						width = stroke.toPx(),
						cap = StrokeCap.Round,
						join = StrokeJoin.Round,
						pathEffect = PathEffect.chainPathEffect(
							PathEffect.dashPathEffect(
								intervals = distanceArray.toFloatArray(),
							),
							PathEffect.dashPathEffect(
								intervals = floatArrayOf(
									measure.length * progress,
									measure.length,
								)
							)
						)
					)
				)

			drawContent()

			if (progress > 0f)
				drawPath(
					path = ribbonPath,
					brush = brush,
					style = Stroke(
						width = stroke.toPx(),
						cap = StrokeCap.Round,
						join = StrokeJoin.Round,
						pathEffect = PathEffect.chainPathEffect(
							PathEffect.dashPathEffect(
								intervals = distanceArray.toFloatArray(),
								phase = distanceArray.first()
							),
							PathEffect.dashPathEffect(
								intervals = floatArrayOf(
									measure.length * progress,
									measure.length,
								)
							)
						)
					)
				)
		}
	}
}

private fun createRibbon(
	start: Offset,
	end: Offset,
	radius: Float,
	startAngle: Float,
	loops: Float,
	resolution: Int = 1000,
): Path {
	val ribbon = Path()

	ribbon.moveTo(start.x, start.y)

	(0..resolution).forEach { i ->
		val t = i / resolution.toFloat()
		val min = min(startAngle, (360f * loops) - startAngle)
		val max = max(startAngle, (360f * loops) - startAngle)
		val degree = lerp(min, max, t)
		if (i == 0) {
			ribbon.polarMoveTo(degree, radius, start)
		}
		ribbon.polarLineTo(
			degree = degree, distance = radius, lerp(start, end, t)
		)
	}

	return ribbon
}