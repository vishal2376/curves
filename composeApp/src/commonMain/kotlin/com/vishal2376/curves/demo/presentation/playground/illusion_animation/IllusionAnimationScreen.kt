package com.vishal2376.curves.demo.presentation.playground.illusion_animation

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.lerp
import com.vishal2376.curves.core.presentation.theme.CurvesTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.pow

/**
 * Author: Vishal Singh (vishal2376)
 */

@Composable
fun IllusionAnimationScreen() {
	Column(
		modifier = Modifier
			.fillMaxSize()
			.background(MaterialTheme.colorScheme.background)
	) {
		val rowCount = 10
		val baseVelocity = 5f
		val acceleration = 1.15f
		val dashRatio = 0.25f

		val ticker by rememberInfiniteTransition().animateFloat(
			initialValue = 0f,
			targetValue = 10000f,
			animationSpec = infiniteRepeatable(
				tween(100000, easing = LinearEasing)
			))

		Canvas(modifier = Modifier.fillMaxSize().background(Color.Black)) {
			val centerY = size.height / 2
			val stripeHeight = (size.height / 2) / rowCount

			val patternLength = 100f
			val dashLength = patternLength * dashRatio
			val gapLength = patternLength - dashLength
			val dashPattern = floatArrayOf(dashLength, gapLength)

			for (i in 0 until rowCount) {
				val speed = baseVelocity * acceleration.pow(i)
				val phase = (ticker * speed) % patternLength

				val pathEffect = PathEffect.dashPathEffect(
					intervals = dashPattern,
					phase = -phase
				)

				val padding = 4f
				val topRowY = centerY - (i * stripeHeight) - (stripeHeight / 2)
				val bottomRowY = centerY + (i * stripeHeight) + (stripeHeight / 2)
				val strokeWidth = stripeHeight - padding

				val rowColor = lerp(
					Color(0xFFFFFFFF),
					Color(0xFF999292),
					i / rowCount.toFloat()
				)

				drawLine(
					color = rowColor,
					start = Offset(0f, topRowY),
					end = Offset(size.width + patternLength, topRowY),
					strokeWidth = strokeWidth,
					pathEffect = pathEffect
				)

				drawLine(
					color = rowColor,
					start = Offset(0f, bottomRowY),
					end = Offset(size.width + patternLength, bottomRowY),
					strokeWidth = strokeWidth,
					pathEffect = pathEffect
				)
			}
		}
	}
}

@Preview
@Composable
private fun IllusionAnimationScreenPreview() {
	CurvesTheme(darkTheme = true, dynamicColor = false) {
		IllusionAnimationScreen()
	}
}