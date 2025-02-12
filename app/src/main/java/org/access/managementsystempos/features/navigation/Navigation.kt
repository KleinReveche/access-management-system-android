package org.access.managementsystempos.features.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.access.managementsystempos.features.kitchen.KitchenScreen
import org.access.managementsystempos.features.kitchen.KitchenScreenDestination
import org.access.managementsystempos.features.login.LoginScreen
import org.access.managementsystempos.features.login.LoginScreenDestination
import org.access.managementsystempos.features.main.MainScreen
import org.access.managementsystempos.features.main.MainScreenDestination
import org.access.managementsystempos.features.pos.POSScreen
import org.access.managementsystempos.features.pos.POSScreenDestination
import org.access.managementsystempos.features.pos.SharedViewModel
import org.access.managementsystempos.features.pos.components.CheckoutScreen
import org.access.managementsystempos.features.sales.SalesScreen
import org.access.managementsystempos.features.sales.SalesScreenDestination
import org.access.managementsystempos.features.settings.SettingsDestination
import org.access.managementsystempos.features.settings.SettingsScreen

interface ScreenDestination

@Composable
fun Navigation(startDestination: ScreenDestination) {
    val navController = rememberNavController()
    val viewModelStoreOwner = LocalViewModelStoreOwner.current

    // Ensure viewModelStoreOwner is not null before calling viewModel()
    val sharedViewModel: SharedViewModel = viewModel(
        viewModelStoreOwner ?: throw IllegalStateException("ViewModelStoreOwner is null")
    )

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
            POSScreen(navController = navController)
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
        composable<KitchenScreenDestination>(
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
            KitchenScreen(navController = navController)
        }

        composable<SalesScreenDestination>(
            enterTransition = {
                return@composable fadeIn(tween(1000))
            },
            exitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start, tween(700)
                )
            },
            popEnterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.End, tween(700)
                )
            }
        ) {
            val cashierName = "John Doe" // Replace with actual cashier name retrieval logic
            SalesScreen(
                navController = navController,
                cashierName = cashierName
            )
        }
        composable(
            route = "checkout_screen/{totalPrice}",
            arguments = listOf(navArgument("totalPrice") { type = NavType.FloatType })
        ) { backStackEntry ->
            val totalPrice = backStackEntry.arguments?.getFloat("totalPrice") ?: 0f
            CheckoutScreen(
                navController = navController,
                totalPrice = totalPrice,
                sharedViewModel = sharedViewModel
            )
        }
    }
}