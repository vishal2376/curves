package com.vishal2376.curves.demo.presentation.playground.split_text_animation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import com.vishal2376.curves.core.presentation.theme.CurvesTheme
import com.vishal2376.curves.core.presentation.theme.SoftBlue
import com.vishal2376.curves.core.presentation.theme.SoftRed
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.abs

@Composable
fun SplitTextAnimationScreen() {

	val text = "Split Text"
	val echoCount = 10

	val yOffsetProgress = remember { Animatable(0f) }
	val letterSpacingProgress = remember { Animatable(0f) }
	val progress = remember { Animatable(0f) }

	val bounceSpring = spring<Float>(
		stiffness = Spring.StiffnessLow,
		dampingRatio = Spring.DampingRatioMediumBouncy
	)
	val lowBounceSpring = spring<Float>(
		stiffness = Spring.StiffnessLow,
		dampingRatio = Spring.DampingRatioNoBouncy
	)

	LaunchedEffect(Unit) {
		delay(1000)

		val upAnimations = listOf(
			launch { progress.animateTo(1f, bounceSpring) },
			launch { yOffsetProgress.animateTo(1f, bounceSpring) }
		)
		upAnimations.joinAll()

		letterSpacingProgress.animateTo(1f, bounceSpring)
		letterSpacingProgress.animateTo(0f, bounceSpring)

		val downAnimations = listOf(
			launch { yOffsetProgress.animateTo(0f, lowBounceSpring) },
			launch { progress.animateTo(0f, lowBounceSpring) }
		)
		downAnimations.joinAll()
	}

	val textMeasurer = rememberTextMeasurer()

	Column(
		modifier = Modifier
			.fillMaxSize()
			.background(MaterialTheme.colorScheme.background)
			.padding(16.dp),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center
	) {
		val textStyle = TextStyle(
			color = lerp(SoftRed, SoftBlue, progress.value),
			fontSize = 48.sp,
			fontWeight = FontWeight.Bold,
			letterSpacing = lerp(0.sp, 30.sp, letterSpacingProgress.value)
		)

		val baseMeasurement = textMeasurer.measure(text, textStyle)
		val baseYOffset = baseMeasurement.size.height / 2f * yOffsetProgress.value
		val clipFraction = 0.5f
		val clipHeight = baseMeasurement.size.height * clipFraction

		Canvas(
			modifier = Modifier
				.aspectRatio(1f)
				.graphicsLayer {
					rotationZ = 45f * progress.value
					rotationX = 45f * progress.value
					scaleX = 1.5f + progress.value
					scaleY = 1.5f + progress.value
				}
		) {
			val baseOffset = Offset(
				center.x - baseMeasurement.size.width / 2,
				center.y - baseMeasurement.size.height / 2
			)

			drawText(
				textMeasurer = textMeasurer,
				text = text,
				style = textStyle,
				topLeft = baseOffset
			)

			(-echoCount..echoCount).forEach { i ->
				if (i == 0) return@forEach

				val echoStyle = textStyle.copy(
					letterSpacing = lerp(0.sp, (20 + 5 * abs(i)).sp, letterSpacingProgress.value),
					color = SoftRed.copy(alpha = 1f - abs(i) / echoCount.toFloat())
				)

				val echoMeasurement = textMeasurer.measure(text, echoStyle)
				val echoBaseOffset = Offset(
					center.x - echoMeasurement.size.width / 2,
					center.y - echoMeasurement.size.height / 2
				)
				val echoOffset = echoBaseOffset.copy(y = echoBaseOffset.y + i * baseYOffset)

				val (top, bottom) = if (i > 0) {
					val b = echoOffset.y + baseMeasurement.size.height
					b - clipHeight to b
				} else {
					echoOffset.y to echoOffset.y + clipHeight
				}

				clipRect(top = top, bottom = bottom) {
					drawText(
						textMeasurer = textMeasurer,
						text = text,
						style = echoStyle,
						topLeft = echoOffset
					)
				}
			}
		}
	}
}

@Preview
@Composable
private fun SplitTextAnimationScreenPreview() {
	CurvesTheme(darkTheme = true, dynamicColor = false) {
		SplitTextAnimationScreen()
	}
}
