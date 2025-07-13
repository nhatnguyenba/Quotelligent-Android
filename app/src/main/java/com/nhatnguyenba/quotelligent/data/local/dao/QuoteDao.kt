package com.nhatnguyenba.quotelligent.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.nhatnguyenba.quotelligent.data.local.entities.CollectionEntity
import com.nhatnguyenba.quotelligent.data.local.entities.QuoteCollectionCrossRef
import com.nhatnguyenba.quotelligent.data.local.entities.QuoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(quote: QuoteEntity)

    @Update
    suspend fun update(quote: QuoteEntity)

    @Query("SELECT * FROM quotes WHERE isFavorite = 1 ORDER BY timestamp DESC")
    fun getFavoriteQuotes(): Flow<List<QuoteEntity>>

    @Query("SELECT * FROM quotes WHERE id IN (SELECT quoteId FROM quote_collection_cross_ref WHERE collectionId = :collectionId)")
    fun getQuotesInCollection(collectionId: Int): Flow<List<QuoteEntity>>

    @Query("SELECT * FROM quotes WHERE id = :id")
    suspend fun getQuoteById(id: String): QuoteEntity?
}

@Dao
interface CollectionDao {
    @Insert
    suspend fun insert(collection: CollectionEntity)

    @Query("SELECT * FROM collections")
    fun getAllCollections(): Flow<List<CollectionEntity>>

    @Query("SELECT * FROM collections WHERE name = :name")
    suspend fun getCollectionByName(name: String): CollectionEntity?
}

@Dao
interface QuoteCollectionCrossRefDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(crossRef: QuoteCollectionCrossRef)

    @Query("DELETE FROM quote_collection_cross_ref WHERE quoteId = :quoteId AND collectionId = :collectionId")
    suspend fun removeFromCollection(quoteId: String, collectionId: Int)

    @Query("SELECT collectionId FROM quote_collection_cross_ref WHERE quoteId = :quoteId")
    suspend fun getCollectionsForQuote(quoteId: String): List<Int>
}