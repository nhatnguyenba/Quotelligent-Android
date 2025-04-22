package com.nhatnguyenba.quotelligent.domain.usecase

import com.nhatnguyenba.quotelligent.domain.model.Quote
import com.nhatnguyenba.quotelligent.domain.repository.QuoteRepository
import javax.inject.Inject

class SearchQuoteUseCase @Inject constructor(
    private val repository: QuoteRepository
) {
    suspend operator fun invoke(keyword: String): List<Quote> = repository.searchQuote(keyword)
}