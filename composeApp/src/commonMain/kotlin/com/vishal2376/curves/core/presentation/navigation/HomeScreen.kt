package com.vishal2376.curves.core.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.vishal2376.curves.demo.presentation.CurvesRegistry
import com.vishal2376.curves.core.presentation.components.BaseCurvesScreen

@Composable
fun HomeScreen(onNavigate: (String) -> Unit) {
	BaseCurvesScreen(
		title = "KMP Curves",
		titleColor = MaterialTheme.colorScheme.primary,
		showBack = false,
	) {
		Column(
			modifier = Modifier
				.fillMaxSize()
				.verticalScroll(rememberScrollState()),
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
		) {
			CurvesRegistry.demos.forEach { demo ->
				Box(
					modifier = Modifier
						.clip(RoundedCornerShape((12.dp)))
						.clickable { onNavigate(demo.route) }
						.fillMaxWidth(.7f)
						.background(MaterialTheme.colorScheme.primaryContainer)
						.border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp))
						.padding(12.dp),
					contentAlignment = Alignment.Center
				) {
					Text(demo.title, color = MaterialTheme.colorScheme.onBackground)
				}
			}
		}
	}
}