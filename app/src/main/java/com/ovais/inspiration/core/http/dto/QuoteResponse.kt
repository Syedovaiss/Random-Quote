package com.ovais.inspiration.core.http.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuoteResponse(
    @SerialName("q")
    val quote: String,
    @SerialName("a")
    val author: String
)
