package com.vishal2376.curves.demo.presentation.common.utils

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.util.lerp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

fun DrawScope.lerpOffset(
	startOffset: Offset,
	endOffset: Offset,
	t: Float,
): Offset {
	return Offset(
		lerp(startOffset.x, endOffset.x, t),
		lerp(startOffset.y, endOffset.y, t)
	)
}

fun Path.polarLineTo(degree: Float, distance: Float, origin: Offset = Offset.Zero) {
	val (x, y) = polarToCart(degree, distance, origin)
	lineTo(x, y)
}

fun Path.polarMoveTo(degree: Float, distance: Float, origin: Offset = Offset.Zero) {
	val (x, y) = polarToCart(degree, distance, origin)
	moveTo(x, y)
}

private fun polarToCart(degree: Float, distance: Float, origin: Offset): Pair<Float, Float> {
	val angle = (-degree * (PI / 180)).toFloat()
	return Pair(
		distance * cos(angle) + origin.x,
		distance * sin(angle) + origin.y
	)
}
