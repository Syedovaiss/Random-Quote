package com.ovais.inspiration.core.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module


val singletonModule = module {
    single<CoroutineDispatcher> { Dispatchers.IO }
    single<CoroutineDispatcher> { Dispatchers.Main }
    single<CoroutineDispatcher> { Dispatchers.Default }
}