package com.vishal2376.curves.demo.presentation

import com.vishal2376.curves.core.presentation.app.CurvesDemo
import com.vishal2376.curves.demo.presentation.playground.beizer_curves.BeizerCurvesDemo
import com.vishal2376.curves.demo.presentation.playground.character_animation.CharacterAnimationDemo
import com.vishal2376.curves.demo.presentation.playground.fibonacci_sphere_animation.FibonacciSphereAnimationDemo
import com.vishal2376.curves.demo.presentation.playground.flash_text_animation.FlashTextAnimationDemo
import com.vishal2376.curves.demo.presentation.playground.flower_animation.FlowerAnimationDemo
import com.vishal2376.curves.demo.presentation.playground.illusion_animation.IllusionAnimationDemo
import com.vishal2376.curves.demo.presentation.playground.proximity_animation_experiment.ProximityAnimationExperimentDemo
import com.vishal2376.curves.demo.presentation.playground.ribbon_animation.RibbonAnimationDemo
import com.vishal2376.curves.demo.presentation.playground.sci_fi_door_animation.SciFiDoorAnimationDemo
import com.vishal2376.curves.demo.presentation.playground.sequence_animation_experiment.SequenceAnimationExperimentDemo
import com.vishal2376.curves.demo.presentation.playground.spiral_animation.SpiralAnimationDemo
import com.vishal2376.curves.demo.presentation.playground.split_text_animation.SplitTextAnimationDemo
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
		ProximityAnimationExperimentDemo,
		SplitTextAnimationDemo,
		FlashTextAnimationDemo,
		CharacterAnimationDemo,
		SciFiDoorAnimationDemo,
		FibonacciSphereAnimationDemo,
		IllusionAnimationDemo
	)
}