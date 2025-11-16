package com.vishal2376.curves.demo.presentation.playground.character_animation.utils

import kotlin.random.Random


object RandomPresets {

	val TRANSLATE_Y = 100f..300f
	val SCALE = listOf(0f, 1f, 5f)
	val ROTATION = listOf(-60f, 0f, 60f)
	val HUE = 60f..360f
	val LIGHTNESS = 0.5f..1f
	val CHAR_DELAY = 50L..150L
	val ANIM_DURATION = 1200..2400

	fun random() = MotionPreset(
		translateY = TRANSLATE_Y.random(),
		scale = SCALE.random(),
		rotation = ROTATION.random(),
		hue = HUE.random(),
		lightness = LIGHTNESS.random(),
		charDelay = CHAR_DELAY.random(),
		animDuration = ANIM_DURATION.random(),
	)

}

data class MotionPreset(
	val translateY: Float,
	val scale: Float,
	val rotation: Float,
	val hue: Float,
	val lightness: Float,
	val charDelay: Long,
	val animDuration: Int,
)


fun ClosedFloatingPointRange<Float>.random() =
	Random.nextFloat() * (endInclusive - start) + start

fun ClosedRange<Long>.random() =
	Random.nextLong(start, endInclusive + 1)

fun ClosedRange<Int>.random() =
	Random.nextInt(start, endInclusive + 1)

