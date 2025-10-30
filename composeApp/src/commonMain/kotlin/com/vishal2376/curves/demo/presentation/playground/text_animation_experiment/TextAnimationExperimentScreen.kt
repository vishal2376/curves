package com.vishal2376.curves.demo.presentation.playground.text_animation_experiment

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vishal2376.curves.core.presentation.theme.CurvesTheme
import com.vishal2376.curves.core.presentation.theme.SoftBlue
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun TextAnimationExperimentScreen() {

	val colors = MaterialTheme.colorScheme

	Column(
		modifier = Modifier
			.fillMaxSize()
			.background(colors.background)
			.padding(16.dp),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center
	) {
		val text = remember { "Hello Vishal" }
		val style = remember {
			TextStyle(
				color = colors.onBackground,
				fontSize = 32.sp,
				fontWeight = FontWeight.Bold,
			)
		}

		val tm = rememberTextMeasurer()
		val res = tm.measure(text, style)

		val progress = Animatable(0f)

		LaunchedEffect(Unit) {
			progress.animateTo(1f, tween(3000))
		}

		Canvas(modifier = Modifier.fillMaxSize()) {
			val path = Path()

			path.apply {
				(0..100).forEach { i ->

				path.moveTo(0f, 0f)
				path.cubicTo(size.width,100f,0f,800f,size.width, size.height)
				}
			}

			val measure = PathMeasure()
			measure.setPath(path, false)

			val textWidth = res.size.width
			text.forEachIndexed { index, char ->
				val rect = res.getBoundingBox(index)
				val distance = rect.left + ((measure.length - textWidth) * progress.value)
				val pathOffset = measure.getPosition(distance)

				drawRect(
					color = colors.primary,
					topLeft = pathOffset - Offset(0f, rect.height * 2f),
					size = rect.size,
					style = Stroke(width = 3f)
				)

				drawText(
					textMeasurer = tm,
					text = char.toString(),
					style = style,
					topLeft = pathOffset - Offset(0f, rect.height * 2f),
					size = Size(rect.width, rect.height)
				)
			}

			drawPath(path, color = colors.onBackground, style = Stroke(width = 10f))

			drawCircle(
				color = SoftBlue,
				radius = 30f,
				center = measure.getPosition((measure.length - textWidth / 2f) * progress.value)
			)

		}
	}
}

@Preview
@Composable
private fun TextAnimationExperimentScreenPreview() {
	CurvesTheme(darkTheme = true, dynamicColor = false) {
		TextAnimationExperimentScreen()
	}
}
