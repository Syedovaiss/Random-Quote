package com.ovais.inspiration.core.http.client

import com.ovais.inspiration.core.http.dto.QuoteResponse
import com.ovais.inspiration.core.http.result.QuoteResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get

fun interface InspirationNetworkClient {
    suspend fun getQuote(): QuoteResult
}

class DefaultInspirationNetworkClient(
    private val client: HttpClient
) : InspirationNetworkClient {
    override suspend fun getQuote(): QuoteResult {
        return try {
            val result = client.get("random").body<List<QuoteResponse>>()
            QuoteResult.Success(result)

        } catch (e: SocketTimeoutException) {
            QuoteResult.Failure("Socket Timeout:${e.message}")

        } catch (e: ClientRequestException) {
            QuoteResult.Failure("Request Error:${e.message}")

        } catch (e: ServerResponseException) {
            QuoteResult.Failure("Server Error:${e.message}")

        } catch (e: Exception) {
            QuoteResult.Failure(e.message.toString())
        }
    }
}