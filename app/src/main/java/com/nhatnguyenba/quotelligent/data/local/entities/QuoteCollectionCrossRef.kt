package com.nhatnguyenba.quotelligent.data.local.entities

import androidx.room.Entity

@Entity(primaryKeys = ["quoteId", "collectionId"], tableName = "quote_collection_cross_ref")
data class QuoteCollectionCrossRef(
    val quoteId: String,
    val collectionId: Int
)