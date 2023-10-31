package com.surodeevartem.imageviewer.presentation.transition

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavBackStackEntry
import com.ramcosta.composedestinations.spec.DestinationStyle

object FadeTransition : DestinationStyle.Animated {
    private const val ANIMATION_DURATION = 100

    override fun AnimatedContentTransitionScope<NavBackStackEntry>.enterTransition() = fadeIn(
        animationSpec = tween(ANIMATION_DURATION),
    )

    override fun AnimatedContentTransitionScope<NavBackStackEntry>.exitTransition() = fadeOut(
        animationSpec = tween(ANIMATION_DURATION),
    )

    override fun AnimatedContentTransitionScope<NavBackStackEntry>.popEnterTransition() = fadeIn(
        animationSpec = tween(ANIMATION_DURATION),
    )

    override fun AnimatedContentTransitionScope<NavBackStackEntry>.popExitTransition() = fadeOut(
        animationSpec = tween(ANIMATION_DURATION),
    )
}
