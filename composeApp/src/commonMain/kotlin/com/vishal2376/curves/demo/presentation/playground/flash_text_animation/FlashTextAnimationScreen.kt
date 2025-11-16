package com.vishal2376.curves.demo.presentation.playground.flash_text_animation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import com.vishal2376.curves.core.presentation.theme.CurvesTheme
import com.vishal2376.curves.core.presentation.theme.SoftBlue
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.PI
import kotlin.math.cos

@Composable
fun FlashTextAnimationScreen() {
	val colors = MaterialTheme.colorScheme

	val echo by remember { mutableStateOf(4) }
	val yOffset by remember { mutableStateOf(80f) }
	val clipProgress = remember { mutableStateOf(0.4f) }

	val delay = 0.9f
	val globalProgress = remember { Animatable(1f) }

	val isRotationEnabled = remember { mutableStateOf(false) }

	LaunchedEffect(Unit) {
		globalProgress.animateTo(
			0f,
			animationSpec = infiniteRepeatable(
				animation = tween(1000, easing = LinearEasing),
				repeatMode = RepeatMode.Restart
			)
		)
	}

	val text = remember { "FLASH" }
	val style =
		TextStyle(
			fontSize = 72.sp,
			color = colors.onBackground,
			fontWeight = FontWeight.Bold,
		)


	val tm = rememberTextMeasurer()
	val textLayoutResult = tm.measure(text = text, style = style)

	Column(
		modifier = Modifier
			.fillMaxSize()
			.background(colors.background)
			.padding(16.dp),
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Canvas(modifier = Modifier.weight(1f).aspectRatio(1f).graphicsLayer {
			if (isRotationEnabled.value) {
				rotationY = lerp(91f, -90f, globalProgress.value)
			}
		}) {
			text.forEachIndexed { textIndex, textValue ->
				val rect = textLayoutResult.getBoundingBox(textIndex)
				val baseOffset =
					center.copy(x = rect.left + (size.width - textLayoutResult.size.width) / 2f)

				val clipHeight = rect.height * clipProgress.value
				val invClipHeight = rect.height * (1f - clipProgress.value)

				val phase = (globalProgress.value + textIndex * delay).mod(1f)
				val wave = (cos(phase * 2 * PI).toFloat() + 1f) / 2f

				(-echo..echo).forEach { i ->
					val offsetY = baseOffset.y + i * yOffset * wave

					val (top, bottom) = when {
						i < 0 -> offsetY to offsetY + clipHeight
						i > 0 -> offsetY + invClipHeight to offsetY + rect.height
						else -> offsetY to offsetY + rect.height
					}

					clipRect(top = top, bottom = bottom) {
						drawText(
							textMeasurer = tm,
							text = textValue.toString(),
							style = style,
							size = rect.size,
							topLeft = baseOffset.copy(y = offsetY)
						)
					}
				}
			}
		}

		Button(
			onClick = {
				isRotationEnabled.value = !isRotationEnabled.value
			},
			colors = ButtonDefaults.buttonColors(
				containerColor = SoftBlue,
				contentColor = colors.background
			)
		) {
			Text(text = "Toggle Rotate")
		}

	}
}


@Preview
@Composable
private fun FlashTextAnimationScreenPreview() {
	CurvesTheme(darkTheme = true, dynamicColor = false) {
		FlashTextAnimationScreen()
	}
}
