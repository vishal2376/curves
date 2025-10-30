package com.vishal2376.curves.demo.presentation.playground.text_reveal_animation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseInBack
import androidx.compose.animation.core.EaseOutBack
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vishal2376.curves.core.presentation.theme.CurvesTheme
import com.vishal2376.curves.core.presentation.theme.SoftBlue
import com.vishal2376.curves.core.presentation.theme.SoftRed
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun TextRevealAnimationScreen() {

	val colors = MaterialTheme.colorScheme
	val gradientColor = Brush.horizontalGradient(listOf(SoftRed, SoftBlue))

	val circleProgress = remember { Animatable(0f) }
	val lineProgress = remember { Animatable(0f) }
	val rotateProgress = remember { Animatable(0f) }
	val posProgress = remember { Animatable(0f) }
	val textProgress = remember { Animatable(0f) }

	val defaultSpec = tween<Float>(800, easing = EaseOutBack)
	val fastSpec = tween<Float>(500, easing = EaseOutBack)
	val fasterSpec = tween<Float>(250, easing = EaseInBack)
	val bounceSpec = tween<Float>(500, easing = EaseOutBack)

	LaunchedEffect(Unit) {
		delay(1000)

		circleProgress.animateTo(1f, bounceSpec)
		circleProgress.animateTo(0.5f, fasterSpec)

		val a = launch { circleProgress.animateTo(0f, defaultSpec) }
		val b = launch { lineProgress.animateTo(1f, fastSpec) }
		a.join()
		b.join()

		val c = launch { rotateProgress.animateTo(1f, defaultSpec) }
		val d = launch { lineProgress.animateTo(0.4f, defaultSpec) }
		c.join()
		d.join()

		posProgress.animateTo(-1f, fastSpec)

		val e = launch { posProgress.animateTo(1f, fastSpec) }
		val f = launch { textProgress.animateTo(1f, defaultSpec) }
		e.join()
		f.join()

		lineProgress.animateTo(0f, fasterSpec)
	}

	Column(
		modifier = Modifier
			.fillMaxSize()
			.background(colors.background)
			.padding(16.dp),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center
	) {

		val text = "Made by Vishal"
		val style = TextStyle(
			brush = gradientColor,
			fontSize = 40.sp
		)
		val tm = rememberTextMeasurer()
		val textLayoutResult = tm.measure(text, style)

		Canvas(modifier = Modifier.aspectRatio(1f)) {
			// circle
			if (circleProgress.value > 0f) {
				val radius = 40f * circleProgress.value
				drawCircle(brush = gradientColor, radius = radius, center = center)
			}

			// expand line
			val lineLength = 200f * lineProgress.value
			val lineStart = center.copy(x = center.x - lineLength)
			val lineEnd = center.copy(x = center.x + lineLength)

			// rotate 360 + 90
			val degree = (360f + 90f) * rotateProgress.value

			// text reveal
			if (textProgress.value > 0f) {
				val w = size.width * textProgress.value
				clipRect(right = w) {
					drawText(
						textLayoutResult = textLayoutResult,
						topLeft = center.copy(
							x = center.x - textLayoutResult.size.width / 2,
							y = center.y - textLayoutResult.size.height / 2
						),
					)
				}
			}

			// draw line
			val pos = ((textLayoutResult.size.width / 2f) + 10f) * posProgress.value
			translate(left = pos) {
				rotate(degree) {
					if (lineProgress.value > 0) {
						drawLine(
							brush = gradientColor,
							start = lineStart,
							end = lineEnd,
							strokeWidth = 20f,
							cap = StrokeCap.Round
						)
					}
				}
			}

		}
	}
}

@Preview
@Composable
private fun TextRevealAnimationScreenPreview() {
	CurvesTheme(darkTheme = true, dynamicColor = false) {
		TextRevealAnimationScreen()
	}
}
