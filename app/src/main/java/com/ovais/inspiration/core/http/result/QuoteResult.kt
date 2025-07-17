package com.ovais.inspiration.core.http.result

import com.ovais.inspiration.core.http.dto.QuoteResponse

sealed interface QuoteResult {
    data class Success(val result: List<QuoteResponse>) : QuoteResult
    data class Failure(val message: String) : QuoteResult
}