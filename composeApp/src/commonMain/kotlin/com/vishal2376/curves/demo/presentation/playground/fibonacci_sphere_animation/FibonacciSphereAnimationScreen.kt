package com.vishal2376.curves.demo.presentation.playground.fibonacci_sphere_animation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.center
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toOffset
import androidx.compose.ui.util.lerp
import com.vishal2376.curves.core.presentation.theme.CurvesTheme
import com.vishal2376.curves.demo.presentation.common.components.CoolSlider
import com.vishal2376.curves.demo.presentation.playground.fibonacci_sphere_animation.utils.generateFibonacciSphere
import com.vishal2376.curves.demo.presentation.playground.fibonacci_sphere_animation.utils.rotateX
import com.vishal2376.curves.demo.presentation.playground.fibonacci_sphere_animation.utils.rotateY
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Author: Vishal Singh (vishal2376)
 */

@Composable
fun FibonacciSphereAnimationScreen() {

	var pointCount by remember { mutableStateOf(300f) }
	var rotationSpeed by remember { mutableStateOf(0.005f) }
	var baseRadius by remember { mutableStateOf(10f) }
	var zNorm by remember { mutableStateOf(0.5f) }
	var hue1 by remember { mutableStateOf(180f) }
	var hue2 by remember { mutableStateOf(260f) }

	Column(
		modifier = Modifier
			.fillMaxSize()
			.background(MaterialTheme.colorScheme.background)
	) {

		Box(modifier = Modifier.weight(1f)) {
			FibonacciSphere(
				pointCount = pointCount.toInt(),
				speed = rotationSpeed,
				maxRadius = baseRadius,
				zNormValue = zNorm,
				colorStart = Color.hsl(hue1, 1f, 0.5f),
				colorEnd = Color.hsl(hue2, 1f, 0.5f)
			)
		}

		Column(
			modifier = Modifier
				.fillMaxWidth()
				.background(
					MaterialTheme.colorScheme.primaryContainer,
					RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
				)
				.padding(24.dp),
			verticalArrangement = Arrangement.spacedBy(12.dp)
		) {
			CoolSlider(
				value = pointCount,
				onValueChange = { pointCount = it },
				label = "Points",
				color = MaterialTheme.colorScheme.primary,
				start = 100f,
				end = 2000f,
			)
			CoolSlider(
				value = rotationSpeed,
				onValueChange = { rotationSpeed = it },
				label = "Speed",
				color = MaterialTheme.colorScheme.primary,
				start = 0f,
				end = 0.02f
			)
			CoolSlider(
				value = baseRadius,
				onValueChange = { baseRadius = it },
				label = "Size",
				color = MaterialTheme.colorScheme.primary,
				start = 1f,
				end = 20f
			)
			CoolSlider(
				value = zNorm,
				onValueChange = { zNorm = it },
				label = "Z Norm",
				color = MaterialTheme.colorScheme.primary,
				start = 0f,
				end = 1f
			)
			CoolSlider(
				value = hue1,
				onValueChange = { hue1 = it },
				label = "Color 1",
				color = Color.hsv(hue1, 1f, 1f),
				start = 0f,
				end = 360f
			)
			CoolSlider(
				value = hue2,
				onValueChange = { hue2 = it },
				label = "Color 2",
				color = Color.hsv(hue2, 1f, 1f),
				start = 0f,
				end = 360f
			)

		}
	}
}

@Composable
fun FibonacciSphere(
	pointCount: Int,
	speed: Float,
	maxRadius: Float,
	zNormValue: Float,
	colorStart: Color,
	colorEnd: Color,
) {
	val points = remember(pointCount) {
		generateFibonacciSphere(pointCount)
	}

	var center by remember { mutableStateOf(Offset.Zero) }
	var rotationX by remember { mutableStateOf(0f) }
	var rotationY by remember { mutableStateOf(0f) }

	val currentSpeed by rememberUpdatedState(speed)

	LaunchedEffect(Unit) {
		while (true) {
			withFrameNanos { _ ->
				rotationY += currentSpeed
			}
		}
	}

	Canvas(
		modifier = Modifier
			.fillMaxSize()
			.onSizeChanged { center = it.center.toOffset() }
			.pointerInput(Unit) {
				detectDragGestures { change, dragAmount ->
					change.consume()
					rotationY += dragAmount.x * 0.005f
					rotationX -= dragAmount.y * 0.005f
				}
			}
	) {
		val sphereRadius = size.minDimension / 2.5f

		// transform rotation & sort z-axis points
		val transformPoints = points.map {
			it.rotateX(rotationX).rotateY(rotationY)
		}.sortedBy { it.z }

		transformPoints.forEach { point ->

			// fake 3d effect
			val zNorm = lerp(point.z, ((point.z + 2f) / 3f), zNormValue)
			val radius = maxRadius * lerp(0.2f, 1f, zNorm)
			val alpha = (zNorm - 0.2f).coerceIn(0.1f, 1f)

			drawCircle(
				brush = Brush.verticalGradient(
					listOf(
						colorStart.copy(alpha = alpha),
						colorEnd.copy(alpha = alpha),
					)
				),
				radius = radius,
				center = center + Offset(point.x * sphereRadius, point.y * sphereRadius)
			)
		}
	}
}

@Preview
@Composable
private fun FibonacciSphereAnimationScreenPreview() {
	CurvesTheme(darkTheme = true, dynamicColor = false) {
		FibonacciSphereAnimationScreen()
	}
}