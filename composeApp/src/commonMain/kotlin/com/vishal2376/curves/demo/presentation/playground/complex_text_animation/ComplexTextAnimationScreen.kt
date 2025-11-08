package com.vishal2376.curves.demo.presentation.playground.complex_text_animation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.SpringSpec
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
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.abs

@Composable
fun ComplexTextAnimationScreen() {

	val colors = MaterialTheme.colorScheme

	val text = remember { "Split Text" }
	val echo = 10


	val yOffsetProgress = remember { Animatable(0f) }
	val lsProgress = remember { Animatable(0f) }
	val progress = remember { Animatable(0f) }

	val bounceSpring: SpringSpec<Float> =
		spring(
			stiffness = Spring.StiffnessLow,
			dampingRatio = Spring.DampingRatioMediumBouncy
		)
	val lowBounceSpring: SpringSpec<Float> =
		spring(
			stiffness = Spring.StiffnessLow,
			dampingRatio = Spring.DampingRatioNoBouncy
		)

	LaunchedEffect(Unit) {
		delay(1000)
		val a = launch { progress.animateTo(1f, bounceSpring) }
		val b = launch { yOffsetProgress.animateTo(1f, bounceSpring) }
		a.join()
		b.join()

		lsProgress.animateTo(1f, bounceSpring)
		lsProgress.animateTo(0f, bounceSpring)

		val c = launch { yOffsetProgress.animateTo(0f, lowBounceSpring) }
		val d = launch { progress.animateTo(0f, lowBounceSpring) }
		c.join()
		d.join()
	}

	Column(
		modifier = Modifier
			.fillMaxSize()
			.background(colors.background)
			.padding(16.dp),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center
	) {
		val style = TextStyle(
			color = lerp(SoftRed, SoftBlue, progress.value),
			fontSize = 48.sp,
			fontWeight = FontWeight.Bold,
			letterSpacing = lerp(0.sp, 30.sp, lsProgress.value)
		)


		val tm = rememberTextMeasurer()
		val res = tm.measure(text, style)

		val yOffset = res.size.height / 2f * yOffsetProgress.value

		val clipProgress = 0.5f
		val clipOffset = (res.size.height * clipProgress)

		Canvas(modifier = Modifier.aspectRatio(1f).graphicsLayer {
			rotationZ = 45f * progress.value
			rotationX = 45f * progress.value

			scaleX = 1.5f + progress.value
			scaleY = 1.5f + progress.value
		}) {

			val centerOffset =
				Offset(center.x - res.size.width / 2, center.y - res.size.height / 2)

			drawText(textMeasurer = tm, text = text, style = style, topLeft = centerOffset)

			(-echo..echo).forEach { i ->
				if (i != 0) {
					val newStyle = style.copy(
						letterSpacing = lerp(
							0.sp,
							(20 + 5 * abs(i)).sp,
							lsProgress.value
						),
						color = SoftRed.copy(alpha = 1f - abs(i) / 10f)
					)

					val newRes = tm.measure(text, newStyle)
					val newCenterOffset = Offset(
						center.x - newRes.size.width / 2,
						center.y - newRes.size.height / 2
					)

					val newOffset = newCenterOffset.copy(y = newCenterOffset.y + i * yOffset)

					var top = newOffset.y
					var bottom = top + clipOffset

					if (i > 0) {
						bottom = newOffset.y + res.size.height
						top = bottom - clipOffset
					}

					clipRect(top = top, bottom = bottom) {
						drawText(
							textMeasurer = tm,
							text = text,
							style = newStyle,
							topLeft = newOffset
						)
					}
				}
			}
		}
	}
}

@Preview
@Composable
private fun ComplexTextAnimationScreenPreview() {
	CurvesTheme(darkTheme = true, dynamicColor = false) {
		ComplexTextAnimationScreen()
	}
}
