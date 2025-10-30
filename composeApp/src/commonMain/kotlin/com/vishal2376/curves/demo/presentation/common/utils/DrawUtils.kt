package com.vishal2376.curves.demo.presentation.common.utils

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.scale
import com.vishal2376.curves.core.presentation.theme.SoftBlue
import com.vishal2376.curves.core.presentation.theme.white

data class PolarPoint(
	val degree: Float = 0f,
	val distance: Float = 0f,
)

fun DrawScope.polarLine(
	pointA: PolarPoint,
	pointB: PolarPoint,
	width: Float,
	progress: Float,
) {
	drawLine(
		color = SoftBlue,
		start = polarToCart(pointA.degree, pointA.distance, center),
		end = polarToCart(pointB.degree, pointB.distance * progress, center),
		strokeWidth = width
	)
}

fun DrawScope.polarPoint(
	point: PolarPoint,
	size: Float = 30f,
	progress: Float,
): PolarPoint {
	scale(
		scaleX = progress,
		scaleY = progress,
	) {
		drawCircle(
			color = SoftBlue,
			radius = size,
			center = polarToCart(point.degree, point.distance, center)
		)
	}

	return point
}


fun DrawScope.polarGrid(
	row: Int,
	col: Int,
	progress: Float = 1f,
	color: Color = Color.White.copy(alpha = 0.5f),
	strokeWidth: Float = 1f,
) {
	val center = Offset(size.width / 2f, size.height / 2f)
	val maxRadius = maxOf(size.width, size.height)

	(1..row).forEach { i ->
		val radius = maxRadius * (i / row.toFloat()) * progress
		drawCircle(
			color = color,
			radius = radius,
			center = center,
			style = Stroke(width = strokeWidth)
		)
	}

	val arcPath = Path()
	(0 until col).forEach { i ->
		val angle = (360f / col) * i

		arcPath.polarMoveTo(angle, 0f, center)
		arcPath.polarLineTo(angle, maxRadius * progress, center)
		drawPath(
			path = arcPath,
			color = color,
			style = Stroke(width = strokeWidth)
		)
	}
}


fun DrawScope.grid(
	row: Int,
	col: Int,
	progress: Float = 1f,
	color: Color = white.copy(alpha = 0.5f),
	strokeWidth: Float = 1f,
) {
	val rowSize = size.height / row
	val colSize = size.width / col

	(0..row).forEach { i ->
		val endX = size.width * progress
		drawLine(
			color = color,
			start = Offset(0f, i * rowSize),
			end = Offset(endX, i * rowSize),
			strokeWidth = strokeWidth
		)
	}

	(0..col).forEach { i ->
		val endY = size.height * progress
		drawLine(
			color = color,
			start = Offset(i * colSize, 0f),
			end = Offset(i * colSize, endY),
			strokeWidth = strokeWidth
		)
	}
}


fun Path.polarLineTo(degree: Float, distance: Float, origin: Offset = Offset.Zero) {
	val (x, y) = polarToCart(degree, distance, origin)
	lineTo(x, y)
}

fun Path.polarMoveTo(degree: Float, distance: Float, origin: Offset = Offset.Zero) {
	val (x, y) = polarToCart(degree, distance, origin)
	moveTo(x, y)
}
