package com.ovais.inspiration.features.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ovais.inspiration.features.home.data.RandomQuoteResult
import com.ovais.inspiration.features.home.domain.GetRandomQuoteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val EMPTY_QUOTES = "Quotes not available!"

class HomeViewModel(
    private val getRandomQuoteUseCase: GetRandomQuoteUseCase,
    private val quoteMapper: QuoteMapper
) : ViewModel() {

    private val _uiState by lazy { MutableStateFlow<HomeUiState>(HomeUiState.Loading) }
    val uiState: StateFlow<HomeUiState>
        get() = _uiState.asStateFlow()

    fun onRetry() {
        fetchRandomQuote()
    }
    init {
        fetchRandomQuote()
    }

    private fun fetchRandomQuote() {
        viewModelScope.launch {
            val result = getRandomQuoteUseCase()
            when (result) {
                is RandomQuoteResult.Success -> {
                    result.quoteResponse?.let { data ->
                        val quote = quoteMapper(data)
                        updateUiState(HomeUiState.Loaded(quote))
                    } ?: run {
                        updateUiState(HomeUiState.Failure(EMPTY_QUOTES))
                    }

                }

                is RandomQuoteResult.Failure -> updateUiState(HomeUiState.Failure(result.message))
            }
        }
    }

    private fun updateUiState(state: HomeUiState) {
        _uiState.update { state }
    }
}