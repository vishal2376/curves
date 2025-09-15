package com.vishal2376.curves

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurvesDemo() {
	val colors = MaterialTheme.colorScheme

	// interpolation factor
	var t by remember { mutableStateOf(0f) }

	// points
	var a by remember { mutableFloatStateOf(0.2f) }
	var b by remember { mutableFloatStateOf(0.5f) }
	var c by remember { mutableFloatStateOf(0.8f) }

	Scaffold(
		modifier = Modifier.fillMaxSize(),
		topBar = {
			TopAppBar(
				title = {
					Text(
						text = "KMP Curve",
						color = colors.primary,
						fontWeight = FontWeight.Bold,
					)
				}
			)
		}
	) { innerPadding ->
		Column(
			modifier = Modifier.fillMaxSize()
				.background(colors.background)
				.padding(innerPadding)
				.padding(16.dp)
		) {
			Canvas(
				modifier = Modifier
					.weight(1f)
					.fillMaxWidth()
					.padding(vertical = 16.dp)
			) {

				val p0 = Offset(size.width * a, size.height * 0.7f)
				val p1 = Offset(size.width * b, size.height * 0.2f)
				val p2 = Offset(size.width * c, size.height * 0.7f)

				val t1 = lerpOffset(p0, p1, t)
				val t2 = lerpOffset(p1, p2, t)
				val t3 = lerpOffset(t1, t2, t)

				// draw lines
				drawLine(colors.primaryContainer, p0, p1, strokeWidth = 8f)
				drawLine(colors.primaryContainer, p1, p2, strokeWidth = 8f)
				drawLine(colors.primaryContainer, t1, t2, strokeWidth = 8f)

				// draw points
				drawCircle(colors.primary, radius = 20f, center = p0)
				drawCircle(colors.primary, radius = 20f, center = p1)
				drawCircle(colors.primary, radius = 20f, center = p2)

				// draw intermediate points
				drawCircle(
					colors.secondary,
					radius = 14f,
					center = t1
				)
				drawCircle(
					colors.secondary,
					radius = 14f,
					center = t2
				)
				drawCircle(
					colors.tertiary,
					radius = 20f,
					center = t3
				)

				// draw traced curve up to t
				val steps = 100
				var prev: Offset? = null

				for (i in 0..(steps * t).toInt()) {
					val tt = i / steps.toFloat()

					val q1 = lerpOffset(p0, p1, tt)
					val q2 = lerpOffset(p1, p2, tt)
					val point = lerpOffset(q1, q2, tt)

					prev?.let {
						drawLine(
							color = colors.primary,
							start = it,
							end = point,
							strokeWidth = 6f
						)
					}
					prev = point
				}
			}

			Column(
				modifier = Modifier.fillMaxWidth()
					.background(colors.surface, RoundedCornerShape(24.dp))
					.padding(24.dp),
				horizontalAlignment = Alignment.CenterHorizontally
			) {
				CoolSlider(
					value = t,
					onValueChange = { t = it },
					label = "t",
					color = colors.primary
				)
				CoolSlider(
					value = a,
					onValueChange = { a = it },
					label = "a",
					color = colors.primary
				)
				CoolSlider(
					value = b,
					onValueChange = { b = it },
					label = "b",
					color = colors.primary
				)
				CoolSlider(
					value = c,
					onValueChange = { c = it },
					label = "c",
					color = colors.primary
				)

				Row {
					Button(
						onClick = {
							t = 0f
							a = 0.2f
							b = 0.5f
							c = 0.8f
						},
					) {
						Text("Reset")
					}
				}
			}
		}
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoolSlider(value: Float, onValueChange: (Float) -> Unit, label: String, color: Color) {
	Row(
		modifier = Modifier.fillMaxWidth(),
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.spacedBy(8.dp)
	) {
		Text(text = label, fontSize = 20.sp, color = color)
		Slider(
			modifier = Modifier.weight(1f),
			value = value,
			onValueChange = onValueChange,
			valueRange = 0f..1f,
			thumb = {
				Box(
					modifier = Modifier
						.size(16.dp)
						.clip(CircleShape)
						.background(MaterialTheme.colorScheme.primary)
				)
			},
			track = {
				Box(
					modifier = Modifier
						.fillMaxWidth()
						.height(16.dp)
						.background(MaterialTheme.colorScheme.background, RoundedCornerShape(8.dp))
				)
			}
		)

		Text(text = value.toString().take(3))
	}
}


private fun DrawScope.lerpOffset(
	startOffset: Offset,
	endOffset: Offset,
	t: Float,
): Offset {
	return Offset(
		lerp(startOffset.x, endOffset.x, t),
		lerp(startOffset.y, endOffset.y, t)
	)
}
