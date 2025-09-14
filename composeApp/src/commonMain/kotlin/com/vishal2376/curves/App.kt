package com.vishal2376.curves

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vishal2376.curves.core.presentation.theme.CurvesTheme

@Composable
fun App(darkTheme: Boolean, dynamicColor: Boolean) {
	CurvesTheme(darkTheme, dynamicColor) {
		Column(
			modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.Center
		) {
			Box(
				modifier = Modifier
					.background(
						MaterialTheme.colorScheme.primaryContainer,
						RoundedCornerShape(16.dp)
					)
					.padding(24.dp)
			) {
				Text("Hello", color = MaterialTheme.colorScheme.primary)
			}
		}
	}
}