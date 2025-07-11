package com.nhatnguyenba.quotelligent.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quotes")
data class QuoteEntity(
    @PrimaryKey
    val id: String = 0.toString(),
    val text: String,
    val author: String,
    val isFavorite: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)