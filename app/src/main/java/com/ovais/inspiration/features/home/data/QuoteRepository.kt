package com.ovais.inspiration.features.home.data

import com.ovais.inspiration.core.http.client.InspirationNetworkClient
import com.ovais.inspiration.core.http.result.QuoteResult

fun interface QuoteRepository {
    suspend fun getRandomQuote(): QuoteResult
}

class DefaultQuoteRepository(
    private val apiClient: InspirationNetworkClient
) : QuoteRepository {
    override suspend fun getRandomQuote(): QuoteResult {
        return apiClient.getQuote()
    }
}