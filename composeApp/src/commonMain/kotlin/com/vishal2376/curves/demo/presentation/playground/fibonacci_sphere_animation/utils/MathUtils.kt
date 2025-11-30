package com.vishal2376.curves.demo.presentation.playground.fibonacci_sphere_animation.utils

import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * Author: Vishal Singh (vishal2376)
 */

data class Point3d(
	val x: Float,
	val y: Float,
	val z: Float,
)

fun generateFibonacciSphere(totalPoints: Int): List<Point3d> {
	val points = ArrayList<Point3d>(totalPoints)

	// Golden Angle
	val phi = PI * (3f - sqrt(5f)) // 137.5 degrees / 2.399 radians

	for (i in 0 until totalPoints) {
		// mapping y-axis from (0 -> (n-1)) to (1 -> -1)
		val y = 1f - (i / (totalPoints - 1f)) * 2f

		// calculate radius at y height
		val r = sqrt(1f - y * y)

		// calculate angle for each point
		val theta = phi * i

		// calculate x, z axis (convert into polar coordinates)
		val x = (r * cos(theta)).toFloat()
		val z = (r * sin(theta)).toFloat()

		points.add(Point3d(x, y, z))
	}

	return points
}

// Rotates a point around the X-axis
fun Point3d.rotateX(angle: Float): Point3d {
	val cosA = cos(angle)
	val sinA = sin(angle)
	val newY = y * cosA - z * sinA
	val newZ = y * sinA + z * cosA
	return copy(y = newY, z = newZ)
}

// Rotates a point around the Y-axis
fun Point3d.rotateY(angle: Float): Point3d {
	val cosA = cos(angle)
	val sinA = sin(angle)
	val newX = x * cosA + z * sinA
	val newZ = -x * sinA + z * cosA
	return copy(x = newX, z = newZ)
}