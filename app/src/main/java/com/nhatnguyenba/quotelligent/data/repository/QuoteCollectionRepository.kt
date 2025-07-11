package com.nhatnguyenba.quotelligent.data.repository

import com.nhatnguyenba.quotelligent.data.local.dao.CollectionDao
import com.nhatnguyenba.quotelligent.data.local.dao.QuoteCollectionCrossRefDao
import com.nhatnguyenba.quotelligent.data.local.dao.QuoteDao
import com.nhatnguyenba.quotelligent.data.local.entities.CollectionEntity
import com.nhatnguyenba.quotelligent.data.local.entities.QuoteCollectionCrossRef
import com.nhatnguyenba.quotelligent.data.local.entities.QuoteEntity
import com.nhatnguyenba.quotelligent.domain.model.Quote
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class QuoteCollectionRepository @Inject constructor(
    private val quoteDao: QuoteDao,
    private val collectionDao: CollectionDao,
    private val crossRefDao: QuoteCollectionCrossRefDao
) {
    suspend fun saveFavorite(quote: Quote) {
        val existing = quoteDao.getQuoteById(id = quote.id)
        if (existing != null) {
            quoteDao.update(existing.copy(isFavorite = true))
        } else {
            quoteDao.insert(
                QuoteEntity(
                    id = quote.id,
                    text = quote.content ?: "",
                    author = quote.author ?: "",
                    isFavorite = true
                )
            )
        }
    }

    suspend fun removeFavorite(quote: Quote) {
        quoteDao.getQuoteById(id = quote.id)?.let {
            quoteDao.update(it.copy(isFavorite = false))
        }
    }

    suspend fun removeFavorite(quoteId: String) {
        quoteDao.getQuoteById(id = quoteId)?.let {
            quoteDao.update(it.copy(isFavorite = false))
        }
    }

    suspend fun createCollection(name: String): Int {
        collectionDao.insert(CollectionEntity(name = name))
        return collectionDao.getCollectionByName(name)?.id ?: 0
    }

    suspend fun addToCollection(quote: Quote, collectionId: Int) {
        val quoteId = quoteDao.getQuoteById(id = quote.id)?.id
            ?: quote.id.also {
                quoteDao.insert(
                    QuoteEntity(
                        id = quote.id,
                        text = quote.content ?: "",
                        author = quote.author ?: ""
                    )
                )
            }

        crossRefDao.insert(QuoteCollectionCrossRef(quoteId, collectionId))
    }

    fun getFavoriteQuotes(): Flow<List<QuoteEntity>> {
        return quoteDao.getFavoriteQuotes()
    }

    fun getAllCollections(): Flow<List<CollectionEntity>> {
        return collectionDao.getAllCollections()
    }

    fun getQuotesInCollection(collectionId: Int): Flow<List<QuoteEntity>> {
        return quoteDao.getQuotesInCollection(collectionId)
    }

    suspend fun removeFromCollection(quoteId: String, collectionId: Int) {
        crossRefDao.removeFromCollection(quoteId, collectionId)
    }

    suspend fun isQuoteFavorite(quote: Quote): Boolean {
        return quoteDao.getQuoteById(id = quote.id)?.isFavorite ?: false
    }
}