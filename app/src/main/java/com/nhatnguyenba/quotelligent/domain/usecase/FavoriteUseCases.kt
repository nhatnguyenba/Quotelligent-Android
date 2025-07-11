package com.nhatnguyenba.quotelligent.domain.usecase

import com.nhatnguyenba.quotelligent.data.local.entities.QuoteEntity
import com.nhatnguyenba.quotelligent.data.repository.QuoteCollectionRepository
import com.nhatnguyenba.quotelligent.domain.model.Quote
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IsQuoteFavoriteUseCase @Inject constructor(private val repository: QuoteCollectionRepository) {
    suspend operator fun invoke(quote: Quote): Boolean {
        return repository.isQuoteFavorite(quote)
    }
}

class ToggleFavoriteUseCase @Inject constructor(private val repository: QuoteCollectionRepository) {
    suspend operator fun invoke(quote: Quote) { // false if already favorite
        if (repository.isQuoteFavorite(quote)) {
            repository.removeFavorite(quote)
        } else {
            repository.saveFavorite(quote)
        }
    }
}

class GetFavoriteQuotesUseCase @Inject constructor(private val repository: QuoteCollectionRepository) {
    operator fun invoke(): Flow<List<QuoteEntity>> {
        return repository.getFavoriteQuotes()
    }
}