package com.vishal2376.curves.demo.presentation.playground.character_animation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vishal2376.curves.core.presentation.theme.CurvesTheme
import com.vishal2376.curves.demo.presentation.playground.character_animation.utils.RandomPresets
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CharacterAnimationScreen() {

	val colors = MaterialTheme.colorScheme
	var restartKey by remember { mutableStateOf(0) }
	var preset by remember { mutableStateOf(RandomPresets.random()) }
	val text = remember { "Compose Multiplatform" }

	// Random Present Animation
	LaunchedEffect(Unit) {
		while (true) {
			preset = RandomPresets.random()
			delay(preset.animDuration + 3000L)
			restartKey++
		}
	}

	Column(
		Modifier.fillMaxSize()
			.background(colors.background)
			.padding(16.dp),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
	) {
		val textStyle = remember {
			TextStyle(
				color = colors.onBackground,
				fontSize = 32.sp,
				fontWeight = FontWeight.Bold
			)
		}

		TypingTextEffect(
			modifier = Modifier.width(500.dp).aspectRatio(1f),
			text = text,
			textStyle = textStyle,
			charDelay = preset.charDelay,
			animDuration = preset.animDuration,
			motionState = { p ->
				CharMotionState(
					alpha = p,
					translate = Offset(0f, (1 - p) * preset.translateY),
					scale = 1f + (1 - p) * preset.scale,
					rotate = (1 - p) * preset.rotation,
					color = Color.hsl(
						hue = (p * preset.hue) % 360f,
						saturation = 1f,
						lightness = p.coerceIn(0.2f, preset.lightness)
					)
				)
			},
			restartKey = restartKey
		)
	}
}

data class CharMotionState(
	val alpha: Float = 1f,
	val scale: Float = 1f,
	val rotate: Float = 0f,
	val translate: Offset = Offset.Zero,
	val color: Color = Color.Unspecified,
)


@Composable
fun TypingTextEffect(
	text: String,
	textStyle: TextStyle,
	charDelay: Long = 50L,
	animDuration: Int = 1000,
	motionState: (progress: Float) -> CharMotionState = { p ->
		CharMotionState(
			alpha = p,
			translate = Offset(0f, (1 - p) * 100f),
			scale = 1f + (1 - p) * 15f,
			rotate = (1 - p) * 60f,
			color = Color.hsl(
				hue = (p * 360f) % 360f,
				saturation = 1f,
				lightness = p.coerceIn(0.2f, 1f)
			)
		)
	},
	restartKey: Int = 0,
	modifier: Modifier = Modifier,
) {

	// text measure
	val tm = rememberTextMeasurer()
	val textLayout = remember(text, textStyle) { tm.measure(text, textStyle) }
	val charLayouts = remember(text, textStyle) {
		text.map { char -> tm.measure(char.toString(), textStyle) }
	}

	// animation state
	val charAnimations = remember(text) {
		List(text.length) { Animatable(0f) }
	}

	LaunchedEffect(text, restartKey) {
		// Reset all characters animation
		charAnimations.forEach { it.snapTo(0f) }

		// Restart Animation
		charAnimations.forEachIndexed { index, animatable ->
			launch {
				delay(index * charDelay)
				animatable.animateTo(1f, tween(animDuration, easing = FastOutLinearInEasing))
			}
		}
	}

	// draw text
	Canvas(modifier = modifier) {
		val textOffset = Offset(
			(size.width - textLayout.size.width) / 2f,
			(size.height - textLayout.size.height) / 2f,
		)


		// center text
		withTransform({
			translate(textOffset.x, textOffset.y)
		}) {
			for (i in text.indices) {
				val charLayout = charLayouts[i]
				val box = textLayout.getBoundingBox(i)

				val progress = charAnimations[i].value
				val state = motionState(progress)

				// char animation
				withTransform({
					translate(box.left + state.translate.x, box.top * state.translate.y)
					rotate(state.rotate)
					scale(state.scale)
				}) {
					drawText(
						textLayoutResult = charLayout,
						topLeft = Offset.Zero,
						alpha = state.alpha,
						brush = SolidColor(state.color)
					)
				}
			}
		}
	}
}

@Preview
@Composable
private fun CharacterAnimationScreenPreview() {
	CurvesTheme(darkTheme = true, dynamicColor = false) {
		CharacterAnimationScreen()
	}
}
