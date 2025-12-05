package com.vishal2376.curves.demo.presentation.playground.sci_fi_door_animation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

val SciFiDark = Color(0xFF0B0F19)
val SciFiMetalDark = Color(0xFF1A2333)
val SciFiMetalLight = Color(0xFF2D3B55)
val NeonCyan = Color(0xFF00F0FF)
val HazardYellow = Color(0xFFFFD700)

@Composable
fun SciFiDoorAnimationScreen() {
	val progress = remember { Animatable(1f) } // 1f = Closed, 0f = Open
	val scope = rememberCoroutineScope()

	// Portable "Pulse" calculation
	val infiniteTransition = rememberInfiniteTransition(label = "pulse")
	val pulse by infiniteTransition.animateFloat(
		initialValue = 0.6f,
		targetValue = 1f,
		animationSpec = infiniteRepeatable(
			animation = tween(1200, easing = LinearEasing),
			repeatMode = RepeatMode.Reverse
		), label = "glow_pulse"
	)

	Box(
		modifier = Modifier
			.fillMaxSize()
			.background(SciFiDark),
		contentAlignment = Alignment.Center
	) {
		Box(
			modifier = Modifier
				.size(300.dp)
				.clip(RoundedCornerShape(16.dp))
				.clickable {
					scope.launch {
						// Open
						progress.animateTo(
							0f,
							tween(800, easing = CubicBezierEasing(0.4f, 0.0f, 0.2f, 1f))
						)
						delay(1500L)
						// Close
						progress.animateTo(
							1f,
							tween(600, easing = CubicBezierEasing(0.4f, 0.0f, 0.2f, 1f))
						)
					}
				}
				.background(Color.Black)
				.border(1.dp, NeonCyan.copy(alpha = 0.5f), RoundedCornerShape(16.dp)),
			contentAlignment = Alignment.Center
		) {
			Text(
				text = "SYSTEM\nSECURE",
				color = NeonCyan,
				fontSize = 24.sp,
				fontWeight = FontWeight.Bold,
				fontFamily = FontFamily.Monospace,
				textAlign = androidx.compose.ui.text.style.TextAlign.Center
			)
		}

		Canvas(modifier = Modifier.fillMaxSize()) {
			val w = size.width
			val h = size.height
			val currentOffset = h * 0.55f * (1f - progress.value)

			val topPath = lockedPanelPath(false, size)
			val bottomPath = lockedPanelPath(true, size)

			val metalGradient = Brush.linearGradient(
				colors = listOf(SciFiMetalDark, SciFiMetalLight, SciFiMetalDark),
				start = Offset(0f, 0f),
				end = Offset(w, h)
			)

			translate(top = -currentOffset) {
				drawPath(topPath, metalGradient)
				clipPath(topPath) {
					drawGridTexture(w, h / 2, SciFiMetalLight.copy(alpha = 0.3f))
				}
				drawPortableNeonStroke(topPath, NeonCyan, 3.dp.toPx(), pulse)
			}

			translate(top = currentOffset) {
				drawPath(bottomPath, metalGradient)
				clipPath(bottomPath) {
					drawGridTexture(w, h / 2, SciFiMetalLight.copy(alpha = 0.3f))
					drawHazardStrip(w, h)
				}
				drawPortableNeonStroke(bottomPath, NeonCyan, 3.dp.toPx(), pulse)
			}
		}
	}
}

fun lockedPanelPath(isBottom: Boolean, size: Size): Path {
	val w = size.width
	val h = size.height
	val cy = h / 2f

	val toothWidth = w * 0.12f
	val toothHeight = h * 0.06f
	val slope = w * 0.04f

	return Path().apply {
		if (!isBottom) {
			moveTo(0f, 0f)
			lineTo(w, 0f)
			lineTo(w, cy - toothHeight)
			lineTo(w / 2 + toothWidth + slope, cy - toothHeight)
			lineTo(w / 2 + toothWidth, cy)
			lineTo(w / 2 - toothWidth, cy)
			lineTo(w / 2 - toothWidth - slope, cy - toothHeight)
			lineTo(0f, cy - toothHeight)
			close()
		} else {
			moveTo(0f, h)
			lineTo(w, h)
			lineTo(w, cy - toothHeight)
			lineTo(w / 2 + toothWidth + slope, cy - toothHeight)
			lineTo(w / 2 + toothWidth, cy)
			lineTo(w / 2 - toothWidth, cy)
			lineTo(w / 2 - toothWidth - slope, cy - toothHeight)
			lineTo(0f, cy - toothHeight)
			close()
		}
	}
}

fun androidx.compose.ui.graphics.drawscope.DrawScope.drawPortableNeonStroke(
	path: Path,
	color: Color,
	baseWidth: Float,
	pulse: Float,
) {
	drawPath(
		path = path,
		color = color.copy(alpha = 0.15f * pulse),
		style = Stroke(width = baseWidth * 6f, cap = StrokeCap.Round, join = StrokeJoin.Round)
	)

	drawPath(
		path = path,
		color = color.copy(alpha = 0.4f * pulse),
		style = Stroke(width = baseWidth * 3f, cap = StrokeCap.Round, join = StrokeJoin.Round)
	)

	drawPath(
		path = path,
		color = color,
		style = Stroke(width = baseWidth, cap = StrokeCap.Round, join = StrokeJoin.Round)
	)

	drawPath(
		path = path,
		color = Color.White.copy(alpha = 0.8f),
		style = Stroke(width = baseWidth * 0.3f, cap = StrokeCap.Round, join = StrokeJoin.Round)
	)
}

fun androidx.compose.ui.graphics.drawscope.DrawScope.drawGridTexture(
	w: Float,
	h: Float,
	color: Color,
) {
	val gridSize = 50f
	var x = 0f
	while (x < w) {
		drawLine(color, Offset(x, 0f), Offset(x, h), strokeWidth = 1f)
		x += gridSize
	}
	var y = 0f
	while (y < h) {
		drawLine(color, Offset(0f, y), Offset(w, y), strokeWidth = 1f)
		y += gridSize
	}
}

fun androidx.compose.ui.graphics.drawscope.DrawScope.drawHazardStrip(w: Float, h: Float) {

	val stripY = h / 2 - 20f
	val stripH = 40f

	drawRect(
		color = Color.Black.copy(alpha = 0.6f),
		topLeft = Offset(w * 0.1f, stripY - 80f),
		size = Size(w * 0.8f, stripH)
	)

	val startX = w * 0.1f
	val endX = w * 0.9f
	var currentX = startX

	while (currentX < endX) {
		drawLine(
			color = HazardYellow.copy(alpha = 0.7f),
			start = Offset(currentX, stripY - 80f),
			end = Offset(currentX + 20f, stripY - 40f),
			strokeWidth = 10f
		)
		currentX += 40f
	}
}