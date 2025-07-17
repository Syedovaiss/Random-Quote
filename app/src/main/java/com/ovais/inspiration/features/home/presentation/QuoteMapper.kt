package com.ovais.inspiration.features.home.presentation

import com.ovais.inspiration.core.http.dto.QuoteResponse

fun interface QuoteMapper {
    operator fun invoke(from: QuoteResponse): QuoteUiData
}

class DefaultQuoteMapper : QuoteMapper {
    override fun invoke(from: QuoteResponse): QuoteUiData {
        return QuoteUiData(
            quote = from.quote,
            author = from.author
        )
    }
}