package com.vishal2376.curves.demo.presentation.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import kotlin.math.pow
import kotlin.math.round
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoolSlider(
	value: Float,
	onValueChange: (Float) -> Unit,
	label: String,
	color: Color,
	start: Float = 0f,
	end: Float = 1f,
	steps: Int = 0,
) {
	val sliderPosition = remember(value, start, end) {
		if (end == start) 0f else (value - start) / (end - start)
	}

	Row(
		modifier = Modifier.fillMaxWidth(),
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.spacedBy(8.dp)
	) {
		Text(text = label, fontSize = 20.sp, color = color)

		Slider(
			modifier = Modifier.weight(1f),
			value = sliderPosition,
			onValueChange = { pos ->

				var mapped = lerp(start, end, pos)

				if (steps > 0) {
					val stepSize = (end - start) / (steps + 1)
					mapped = (mapped / stepSize).roundToInt() * stepSize
				}

				onValueChange(mapped)
			},
			valueRange = 0f..1f,
			steps = steps,
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
						.background(
							MaterialTheme.colorScheme.surfaceVariant,
							RoundedCornerShape(8.dp)
						)
				)
			}
		)

		Text(
			text = value.format(),
			color = MaterialTheme.colorScheme.onPrimaryContainer,
			fontSize = 16.sp
		)
	}
}

fun Float.format(decimals: Int = 2): String {
	val factor = 10f.pow(decimals)
	val rounded = round(this * factor) / factor

	val str = rounded.toString()

	return if (str.contains(".")) {
		val parts = str.split(".")
		val padded = parts[1].padEnd(decimals, '0')
		"${parts[0]}.$padded"
	} else {
		str + "." + "0".repeat(decimals)
	}
}
