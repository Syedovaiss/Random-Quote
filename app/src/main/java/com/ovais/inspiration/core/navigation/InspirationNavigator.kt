package com.ovais.inspiration.core.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.ovais.inspiration.features.home.presentation.HomeScreen

@Composable
fun InspirationNavigator(
    paddingValues: PaddingValues
) {
    val backStack = remember { mutableListOf<AppRoutes>(AppRoutes.Home) }
    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = { key ->
            when (key) {
                is AppRoutes.Home -> NavEntry(key) {
                    HomeScreen(
                        paddingValues = paddingValues
                    )
                }
            }
        }
    )
}