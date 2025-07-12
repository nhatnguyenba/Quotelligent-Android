package com.nhatnguyenba.quotelligent.domain.usecase

import com.nhatnguyenba.quotelligent.data.local.entities.CollectionEntity
import com.nhatnguyenba.quotelligent.data.local.entities.QuoteEntity
import com.nhatnguyenba.quotelligent.data.repository.QuoteCollectionRepository
import com.nhatnguyenba.quotelligent.domain.model.Quote
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateCollectionUseCase @Inject constructor(private val repository: QuoteCollectionRepository) {
    suspend operator fun invoke(name: String): Int {
        return repository.createCollection(name)
    }
}

class AddToCollectionUseCase @Inject constructor(private val repository: QuoteCollectionRepository) {
    suspend operator fun invoke(quote: Quote, collectionId: Int) {
        repository.addToCollection(quote, collectionId)
    }
}

class GetCollectionsUseCase @Inject constructor(private val repository: QuoteCollectionRepository) {
    operator fun invoke(): Flow<List<CollectionEntity>> {
        return repository.getAllCollections()
    }
}

class GetQuotesInCollectionUseCase @Inject constructor(private val repository: QuoteCollectionRepository) {
    operator fun invoke(collectionId: Int): Flow<List<QuoteEntity>> {
        return repository.getQuotesInCollection(collectionId)
    }
}

class RemoveFromCollectionUseCase @Inject constructor(private val repository: QuoteCollectionRepository) {
    suspend operator fun invoke(quote: QuoteEntity, collectionId: Int) {
        repository.removeFromCollection(quote.id, collectionId)
    }
}

class RemoveFromFavoriteUseCase @Inject constructor(private val repository: QuoteCollectionRepository) {
    suspend operator fun invoke(quoteId: String) {
        repository.removeFavorite(quoteId)
    }
}