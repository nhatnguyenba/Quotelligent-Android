package com.nhatnguyenba.quotelligent.domain.usecase

import com.nhatnguyenba.quotelligent.domain.model.Quote
import com.nhatnguyenba.quotelligent.domain.repository.QuoteRepository
import javax.inject.Inject

class IsQuoteSavedUseCase @Inject constructor(
    private val repository: QuoteRepository
) {
    suspend operator fun invoke(quote: Quote): Boolean {
        return repository.isQuoteSaved(quote)
    }
}