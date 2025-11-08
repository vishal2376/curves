package com.vishal2376.curves.demo.presentation

import com.vishal2376.curves.core.presentation.app.CurvesDemo
import com.vishal2376.curves.demo.presentation.playground.beizer_curves.BeizerCurvesDemo
import com.vishal2376.curves.demo.presentation.playground.complex_text_animation.ComplexTextAnimationDemo
import com.vishal2376.curves.demo.presentation.playground.flower_animation.FlowerAnimationDemo
import com.vishal2376.curves.demo.presentation.playground.ribbon_animation.RibbonAnimationDemo
import com.vishal2376.curves.demo.presentation.playground.sequence_animation_experiment.SequenceAnimationExperimentDemo
import com.vishal2376.curves.demo.presentation.playground.spiral_animation.SpiralAnimationDemo
import com.vishal2376.curves.demo.presentation.playground.text_animation_experiment.TextAnimationExperimentDemo
import com.vishal2376.curves.demo.presentation.playground.text_reveal_animation.TextRevealAnimationDemo
import com.vishal2376.curves.demo.presentation.playground.wave_animation.WaveAnimationDemo

object CurvesRegistry {
	val demos: List<CurvesDemo> = listOf(
		BeizerCurvesDemo,
		SpiralAnimationDemo,
		FlowerAnimationDemo,
		WaveAnimationDemo,
		RibbonAnimationDemo,
		SequenceAnimationExperimentDemo,
		TextAnimationExperimentDemo,
		TextRevealAnimationDemo,
		ComplexTextAnimationDemo,
	)
}