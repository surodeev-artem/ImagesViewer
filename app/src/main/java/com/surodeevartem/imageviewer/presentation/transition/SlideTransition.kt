package com.surodeevartem.imageviewer.presentation.transition

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavBackStackEntry
import com.ramcosta.composedestinations.spec.DestinationStyle

object SlideTransition : DestinationStyle.Animated {
    private const val ANIMATION_DURATION = 500

    override fun AnimatedContentTransitionScope<NavBackStackEntry>.enterTransition() = slideIntoContainer(
        AnimatedContentTransitionScope.SlideDirection.Left,
        animationSpec = tween(ANIMATION_DURATION),
    )

    override fun AnimatedContentTransitionScope<NavBackStackEntry>.exitTransition() = slideOutOfContainer(
        AnimatedContentTransitionScope.SlideDirection.Left,
        animationSpec = tween(ANIMATION_DURATION),
    )

    override fun AnimatedContentTransitionScope<NavBackStackEntry>.popEnterTransition() = slideIntoContainer(
        AnimatedContentTransitionScope.SlideDirection.Right,
        animationSpec = tween(ANIMATION_DURATION),
    )

    override fun AnimatedContentTransitionScope<NavBackStackEntry>.popExitTransition() = slideOutOfContainer(
        AnimatedContentTransitionScope.SlideDirection.Right,
        animationSpec = tween(ANIMATION_DURATION),
    )
}
