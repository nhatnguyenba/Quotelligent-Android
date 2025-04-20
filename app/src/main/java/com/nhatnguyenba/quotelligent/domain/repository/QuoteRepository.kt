package com.nhatnguyenba.quotelligent.domain.repository

import com.nhatnguyenba.quotelligent.domain.model.Quote

interface QuoteRepository {
    suspend fun getRandomQuote(): Quote
    suspend fun getBackgroundImage(tags: List<String>): String
}