package com.vishal2376.curves.demo.presentation.common.utils

import androidx.compose.ui.geometry.Offset
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

fun Float.toRadians(): Float {
	return this * (PI.toFloat() / 180)
}

fun Float.toDegrees(): Float {
	return this * (180 / PI.toFloat())
}

fun polarToCart(degree: Float, distance: Float, origin: Offset): Offset {
	val angle = (-degree * (PI / 180)).toFloat()
	return Offset(
		distance * cos(angle) + origin.x,
		distance * sin(angle) + origin.y
	)
}
