package com.vishal2376.curves.demo.presentation.playground.qr_3d_animation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import com.vishal2376.curves.demo.presentation.common.utils.toRadians
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import qrcode.raw.QRCodeProcessor
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.tan


@Composable
fun Qr3dAnimationScreen() {
	val qrText = "https://github.com/vishal2376/Curves"

	val processor = remember { QRCodeProcessor(qrText) }
	val encoded = remember { processor.encode() }
	val gridSize = encoded.size

	val progress = remember { Animatable(0f) }
	val scope = rememberCoroutineScope()
	var is3d by remember { mutableStateOf(false) }

	val infiniteTransition = rememberInfiniteTransition(label = "infinite_sweep")
	val waveThickness = 18f

	val linePosition by infiniteTransition.animateFloat(
		initialValue = -waveThickness,
		targetValue = (gridSize * 2).toFloat() + waveThickness,
		animationSpec = infiniteRepeatable(
			animation = tween(4000, easing = LinearEasing),
			repeatMode = RepeatMode.Restart
		),
		label = "line_position"
	)

	fun toggleView() {
		is3d = !is3d
		scope.launch {
			progress.animateTo(
				targetValue = if (is3d) 1f else 0f,
				animationSpec = spring(dampingRatio = 0.65f, stiffness = 200f)
			)
		}
	}

	val bgColor = Color(0xFF11111B)
	val colorDeepBlue = Color(27, 71, 107)
	val colorCherryBlossom = Color(255, 140, 160)

	Box(
		modifier = Modifier
			.fillMaxSize()
			.background(bgColor)
			.clickable(
				interactionSource = remember { MutableInteractionSource() },
				indication = null
			) { toggleView() }
	) {
		Canvas(modifier = Modifier.fillMaxSize()) {
			val p = progress.value

			val flatCellSize = size.minDimension / (gridSize + 4)
			val startXFlat = (size.width - gridSize * flatCellSize) / 2f
			val startYFlat = (size.height - gridSize * flatCellSize) / 2f
			val angleRad = (30f).toRadians()
			val cosA = cos(angleRad)
			val sinA = sin(angleRad)
			val isoScale = 1.0f
			val isoCellSize = (size.minDimension / (gridSize * 2f)) * isoScale
			val dx = isoCellSize * cosA
			val dy = isoCellSize * sinA
			val startXIso = size.width / 2f
			val startYIso = size.height * 0.35f

			val maxHeight = 180f

			fun getH(x: Int, z: Int): Float {
				if (x !in 0 until gridSize || z !in 0 until gridSize) return 0f
				if (!encoded[z][x].dark) return 0f

				val cx = gridSize / 2f
				val vAngle = 45.0
				val tanA = tan(vAngle.toFloat().toRadians()) * 0.5f
				val leftArm = abs((x - cx) + (z - (gridSize - 1)) * tanA)
				val rightArm = abs((x - cx) - (z - (gridSize - 1)) * tanA)

				val spread = 7f
				val t = max(0f, 1f - min(leftArm, rightArm) / spread)
				val baseHeight = t * maxHeight

				val blockPos = x + z
				val distanceFromLine = abs(blockPos - linePosition)

				val sweepMultiplier = if (distanceFromLine < waveThickness) {
					(cos((distanceFromLine / waveThickness) * PI).toFloat() + 1f) / 2f
				} else {
					0f
				}

				return baseHeight * sweepMultiplier
			}

			val sun = Vec3(-0.6f, 1f, 0.8f).normalize()
			val ambientLight = 0.4f

			fun getVertex(gridX: Float, gridZ: Float, height: Float): Offset {
				val flatX = startXFlat + gridX * flatCellSize
				val flatY = startYFlat + gridZ * flatCellSize
				val isoX = startXIso + (gridX - gridZ) * dx
				val isoY = startYIso + (gridX + gridZ) * dy - height

				return Offset(
					x = lerp(flatX, isoX, p),
					y = lerp(flatY, isoY, p)
				)
			}

			for (z in 0 until gridSize) {
				for (x in 0 until gridSize) {
					if (encoded[z][x].dark) {

						val targetHeight = getH(x, z)
						val h = lerp(0f, targetHeight, p)

						val neighbors = listOf(
							getH(x - 1, z), getH(x + 1, z), getH(x, z - 1), getH(x, z + 1)
						)
						val ao = 1f - (neighbors.count { it > targetHeight } * 0.15f)
						val appliedAo = lerp(1f, ao, p)

						val heightFraction = if (maxHeight > 0) (targetHeight / maxHeight) else 0f
						val blockColor =
							lerpColor(colorDeepBlue, colorCherryBlossom, heightFraction)

						val topColor =
							shade(blockColor, Vec3(0f, 1f, 0f), sun, ambientLight, appliedAo)
						val rightColor =
							shade(blockColor, Vec3(1f, 0f, 0f), sun, ambientLight, appliedAo)
						val leftColor =
							shade(blockColor, Vec3(0f, 0f, 1f), sun, ambientLight, appliedAo)

						val t0 = getVertex(x.toFloat(), z.toFloat(), h)
						val t1 = getVertex(x + 1f, z.toFloat(), h)
						val t2 = getVertex(x + 1f, z + 1f, h)
						val t3 = getVertex(x.toFloat(), z + 1f, h)

						val topPath = Path().apply {
							moveTo(t0.x, t0.y); lineTo(t1.x, t1.y)
							lineTo(t2.x, t2.y); lineTo(t3.x, t3.y); close()
						}
						drawPath(topPath, color = topColor)

						if (p > 0.01f) {
							val b1 = getVertex(x + 1f, z.toFloat(), 0f)
							val b2 = getVertex(x + 1f, z + 1f, 0f)
							val b3 = getVertex(x.toFloat(), z + 1f, 0f)

							val rightPath = Path().apply {
								moveTo(t1.x, t1.y); lineTo(t2.x, t2.y)
								lineTo(b2.x, b2.y); lineTo(b1.x, b1.y); close()
							}
							drawPath(rightPath, color = rightColor)

							val leftPath = Path().apply {
								moveTo(t3.x, t3.y); lineTo(t2.x, t2.y)
								lineTo(b2.x, b2.y); lineTo(b3.x, b3.y); close()
							}
							drawPath(leftPath, color = leftColor)
						}
					}
				}
			}
		}
	}
}

data class Vec3(val x: Float, val y: Float, val z: Float) {
	fun dot(other: Vec3) = x * other.x + y * other.y + z * other.z
	fun normalize(): Vec3 {
		val len = sqrt(x * x + y * y + z * z)
		return if (len == 0f) this else Vec3(x / len, y / len, z / len)
	}
}

fun shade(base: Color, normal: Vec3, light: Vec3, ambient: Float, ao: Float): Color {
	val diffuse = max(0f, normal.dot(light))
	val intensity = ambient + (1f - ambient) * diffuse
	val final = intensity * ao
	return base.copy(
		red = (base.red * final).coerceIn(0f, 1f),
		green = (base.green * final).coerceIn(0f, 1f),
		blue = (base.blue * final).coerceIn(0f, 1f)
	)
}

fun lerp(start: Float, stop: Float, fraction: Float): Float {
	return start + (stop - start) * fraction
}

fun lerpColor(start: Color, stop: Color, fraction: Float): Color {
	val f = fraction.coerceIn(0f, 1f)
	return Color(
		red = lerp(start.red, stop.red, f),
		green = lerp(start.green, stop.green, f),
		blue = lerp(start.blue, stop.blue, f),
		alpha = lerp(start.alpha, stop.alpha, f)
	)
}

@Preview
@Composable
private fun Qr3dAnimationScreenPreview() {
	Qr3dAnimationScreen()
}