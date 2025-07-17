package com.ovais.inspiration.widget

import android.util.Log
import com.ovais.inspiration.BuildConfig
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

object WidgetNetworkClient {
    val instance: HttpClient by lazy {
        HttpClient(Android) {
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
                    host = BuildConfig.HOST
                }
                header(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            }
        }
    }
}