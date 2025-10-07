package com.vishal2376.curves.demo.presentation.common.utils

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.util.lerp

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