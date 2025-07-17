package com.ovais.inspiration.core.di

import android.util.Log
import com.ovais.inspiration.BuildConfig
import com.ovais.inspiration.core.http.client.DefaultInspirationNetworkClient
import com.ovais.inspiration.core.http.client.InspirationNetworkClient
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.header
import io.ktor.client.statement.request
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module


val networkModule = module {
    single { BuildConfig.HOST }
    single<InspirationNetworkClient> { DefaultInspirationNetworkClient(get()) }

    single {
        HttpClient(Android) {
            val appHost: String = get()
            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
            }
            engine {
                connectTimeout = 60000
                socketTimeout = 60000
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        if (BuildConfig.DEBUG)
                            Log.d("Network Logs:", message)
                    }
                }
                level = if (BuildConfig.DEBUG) LogLevel.ALL else LogLevel.NONE
            }
            install(ResponseObserver) {
                onResponse { response ->
                    if (BuildConfig.DEBUG) {
                        Log.d("Http Request Host:", response.request.url.host)
                        Log.d("Http Status:", response.status.value.toString())
                    }
                }
            }
            install(DefaultRequest) {
                url {
                    protocol = URLProtocol.HTTPS
                    host = appHost
                }
                header(HttpHeaders.ContentType, ContentType.Application.Json.toString())

            }
        }
    }
}