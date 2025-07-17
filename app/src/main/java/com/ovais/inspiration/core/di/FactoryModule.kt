package com.ovais.inspiration.core.di

import com.ovais.inspiration.features.home.data.DefaultQuoteRepository
import com.ovais.inspiration.features.home.data.QuoteRepository
import com.ovais.inspiration.features.home.domain.DefaultGetRandomQuoteUseCase
import com.ovais.inspiration.features.home.domain.GetRandomQuoteUseCase
import com.ovais.inspiration.features.home.presentation.DefaultQuoteMapper
import com.ovais.inspiration.features.home.presentation.QuoteMapper
import org.koin.dsl.bind
import org.koin.dsl.module


val factoryModule = module {
    factory { DefaultQuoteRepository(get()) } bind QuoteRepository::class
    factory { DefaultGetRandomQuoteUseCase(get()) } bind GetRandomQuoteUseCase::class
    factory { DefaultQuoteMapper() } bind QuoteMapper::class
}