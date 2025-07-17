package com.ovais.inspiration.features.home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel


@Composable
fun HomeScreen(
    paddingValues: PaddingValues = PaddingValues(),
    viewModel: HomeViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(Color.White)
    ) {
        when (uiState) {
            is HomeUiState.Loading -> HomeLoadingView()
            is HomeUiState.Failure -> HomeErrorView(
                message = (uiState as HomeUiState.Failure).message,
                onRetry = viewModel::onRetry
            )

            is HomeUiState.Loaded -> HomeSuccessView((uiState as HomeUiState.Loaded).quoteUiData)
        }

    }
}