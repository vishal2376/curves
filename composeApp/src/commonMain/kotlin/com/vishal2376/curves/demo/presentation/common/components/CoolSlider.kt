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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


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

		Text(text = value.toString().take(3),color = MaterialTheme.colorScheme.onPrimaryContainer)
	}
}