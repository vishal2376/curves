package com.vishal2376.curves.demo.presentation.playground.proximity_animation_experiment

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.vishal2376.curves.core.presentation.theme.CurvesTheme
import com.vishal2376.curves.core.presentation.theme.SoftRed
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ProximityAnimationExperimentScreen() {

	val colors = MaterialTheme.colorScheme

	Column(
		modifier = Modifier
			.fillMaxSize()
			.background(colors.background)
			.padding(16.dp),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center
	) {
		ProximityLines()
	}
}

@Composable
fun ProximityLines() {
	var pointer by remember { mutableStateOf(Offset.Zero) }
	val spacing = 70
	val r = 20
	val c = 20
	val lines = remember {
		List(r) { row ->
			List(c) { col ->
				Offset(col * spacing.toFloat() + 100f, row * spacing.toFloat() + 100f)
			}
		}.flatten()
	}

	Box(
		modifier = Modifier
			.pointerInput(Unit) {
				awaitPointerEventScope {
					while (true) {
						val event = awaitPointerEvent()
						pointer = event.changes.first().position
					}
				}
			}
			.padding(32.dp),
		contentAlignment = Alignment.Center
	) {
		Canvas(modifier = Modifier.aspectRatio(1f)) {
			lines.forEach { pos ->
				val distance = (pos - pointer).getDistance()
				val normalized = (1f - (distance / 300f)).coerceIn(0f, 1f)

				val rotation = lerp(-90f, 90f, normalized)
				val color = lerp(Color.DarkGray, SoftRed, normalized)

				rotate(rotation, pivot = pos) {
					drawLine(
						color = color,
						start = Offset(pos.x - 20, pos.y),
						end = Offset(pos.x + 20, pos.y),
						strokeWidth = 10f,
						cap = StrokeCap.Round
					)
				}
			}
		}
	}
}


@Preview
@Composable
private fun ProximityAnimationExperimentScreenPreview() {
	CurvesTheme(darkTheme = true, dynamicColor = false) {
		ProximityAnimationExperimentScreen()
	}
}










