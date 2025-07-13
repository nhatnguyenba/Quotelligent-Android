package com.nhatnguyenba.quotelligent.domain.repository

import com.nhatnguyenba.quotelligent.domain.model.Quote

interface QuoteRepository {
    suspend fun getRandomQuote(): Quote
    suspend fun getBackgroundImage(tags: List<String>): String
    suspend fun searchQuote(keyword: String): List<Quote>
    suspend fun getQuoteById(quoteId: String): Quote?
    suspend fun getQuotesByAuthor(authorId: String): List<Quote>
    suspend fun getQuotesByCategory(categoryId: String): List<Quote>
    suspend fun isQuoteSaved(quote: Quote): Boolean
}