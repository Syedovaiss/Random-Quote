package com.ovais.inspiration.features.home.data

import com.ovais.inspiration.core.http.dto.QuoteResponse

sealed interface RandomQuoteResult {
    data class Success(val quoteResponse: QuoteResponse?) : RandomQuoteResult
    data class Failure(val message: String) : RandomQuoteResult
}