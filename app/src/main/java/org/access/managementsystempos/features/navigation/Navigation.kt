package org.access.managementsystempos.features.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.access.managementsystempos.data.PreferenceKey
import org.access.managementsystempos.data.PreferenceKeys
import org.access.managementsystempos.data.readDataStore
import org.access.managementsystempos.features.kitchen.KitchenDestination
import org.access.managementsystempos.features.kitchen.KitchenScreen
import org.access.managementsystempos.features.login.LoginScreen
import org.access.managementsystempos.features.login.LoginScreenDestination
import org.access.managementsystempos.features.main.MainScreen
import org.access.managementsystempos.features.main.MainScreenDestination
import org.access.managementsystempos.features.pos.POSScreen
import org.access.managementsystempos.features.pos.POSScreenDestination
import org.access.managementsystempos.features.pos.SharedViewModel
import org.access.managementsystempos.features.settings.SettingsDestination
import org.access.managementsystempos.features.settings.SettingsScreen

interface ScreenDestination

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    var startDestination: ScreenDestination by remember { mutableStateOf(LoginScreenDestination) }
    val sharedViewModel: SharedViewModel = viewModel()

    LaunchedEffect(Unit) {
        val loginToken = context.readDataStore(PreferenceKeys[PreferenceKey.LOGIN_TOKEN]!!)

        if (!loginToken.isNullOrEmpty()) {
            startDestination = MainScreenDestination
        }
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<MainScreenDestination>(
            enterTransition = {
                return@composable fadeIn(tween(1000))
            }, exitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start, tween(700)
                )
            }, popEnterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.End, tween(700)
                )
            }
        ) {
            MainScreen(navController)
        }
        composable<LoginScreenDestination>(

            enterTransition = {
                return@composable fadeIn(tween(1000))
            }, exitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start, tween(700)
                )
            }, popEnterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.End, tween(700)
                )
            }
        ) {
            LoginScreen(navController)
        }
        composable<POSScreenDestination>(
            enterTransition = {
                return@composable fadeIn(tween(1000))
            }, exitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start, tween(700)
                )
            }, popEnterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.End, tween(700)
                )
            }
        ) { _ ->
            POSScreen(sharedViewModel = sharedViewModel, navController = navController)
        }
        composable<SettingsDestination>(

            enterTransition = {
                return@composable fadeIn(tween(1000))
            }, exitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start, tween(700)
                )
            }, popEnterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.End, tween(700)
                )
            }
        ) {
            SettingsScreen()
        }
        composable<KitchenDestination>(

            enterTransition = {
                return@composable fadeIn(tween(1000))
            }, exitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start, tween(700)
                )
            }, popEnterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.End, tween(700)
                )
            }
        ) {
            KitchenScreen(sharedViewModel = sharedViewModel)
        }
    }
}