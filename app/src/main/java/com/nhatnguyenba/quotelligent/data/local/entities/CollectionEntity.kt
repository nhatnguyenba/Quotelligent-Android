package com.nhatnguyenba.quotelligent.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "collections")
data class CollectionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val isFavoriteCollection: Boolean = false,
    val name: String,
    val createdAt: Long = System.currentTimeMillis()
)