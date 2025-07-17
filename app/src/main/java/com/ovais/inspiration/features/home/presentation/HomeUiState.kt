package com.ovais.inspiration.features.home.presentation

sealed interface HomeUiState {
    data class Loaded(val quoteUiData: QuoteUiData) : HomeUiState
    data object Loading : HomeUiState
    data class Failure(val message: String) : HomeUiState
}