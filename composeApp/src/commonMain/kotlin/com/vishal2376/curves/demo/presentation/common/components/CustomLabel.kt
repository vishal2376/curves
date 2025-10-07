package com.vishal2376.curves.demo.presentation.common.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun CustomLabel(text: String, value: Int) {
	Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
		Text(
			text,
			color = MaterialTheme.colorScheme.primary
		)
		Text(
			text = value.toString(),
			color = MaterialTheme.colorScheme.secondary
		)
	}
}
