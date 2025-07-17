package com.ovais.inspiration.features.home.domain

import com.ovais.inspiration.core.http.result.QuoteResult
import com.ovais.inspiration.features.home.data.QuoteRepository
import com.ovais.inspiration.features.home.data.RandomQuoteResult

fun interface GetRandomQuoteUseCase {
    suspend operator fun invoke(): RandomQuoteResult
}

class DefaultGetRandomQuoteUseCase(
    private val quoteRepository: QuoteRepository
) : GetRandomQuoteUseCase {

    override suspend fun invoke(): RandomQuoteResult {
        return when (val response = quoteRepository.getRandomQuote()) {
            is QuoteResult.Success -> RandomQuoteResult.Success(response.result.firstOrNull())
            is QuoteResult.Failure -> RandomQuoteResult.Failure(response.message)
        }
    }
}