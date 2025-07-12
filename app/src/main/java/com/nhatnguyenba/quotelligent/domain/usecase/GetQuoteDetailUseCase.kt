package com.nhatnguyenba.quotelligent.domain.usecase

import com.nhatnguyenba.quotelligent.domain.model.Quote
import com.nhatnguyenba.quotelligent.domain.repository.QuoteRepository
import javax.inject.Inject

class GetQuoteDetailUseCase @Inject constructor(
    private val repository: QuoteRepository
) {
    suspend operator fun invoke(quoteId: String): Quote? {
        return repository.getQuoteById(quoteId)
    }
}