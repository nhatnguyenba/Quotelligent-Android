package com.nhatnguyenba.quotelligent.domain.model

data class Quote(
    val id: String? = "",
    val content: String? = "",
    val author: String? = "",
    val tags: List<String>? = emptyList()
)