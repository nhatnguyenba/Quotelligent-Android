package com.nhatnguyenba.quotelligent.data.repository

import android.util.Log
import com.nhatnguyenba.quotelligent.data.local.dao.QuoteCollectionCrossRefDao
import com.nhatnguyenba.quotelligent.data.remote.api.FavQsQuoteApiService
import com.nhatnguyenba.quotelligent.data.remote.api.PexelsApiService
import com.nhatnguyenba.quotelligent.data.remote.mapper.toDomain
import com.nhatnguyenba.quotelligent.domain.model.Quote
import com.nhatnguyenba.quotelligent.domain.repository.QuoteRepository
import java.io.IOException
import javax.inject.Inject

class QuoteRepositoryImpl @Inject constructor(
    private val quoteApi: FavQsQuoteApiService,
    private val pexelsApi: PexelsApiService,
    private val crossRefDao: QuoteCollectionCrossRefDao
) : QuoteRepository {

    override suspend fun getRandomQuote(): Quote {
        val response = quoteApi.getRandomQuote()
        if (!response.isSuccessful) {
            IOException("Failed to get quote").printStackTrace()
            return Quote()
        }
        Log.d("NHAT", "getRandomQuote: " + response.body())
        return response.body().toDomain()
    }

    override suspend fun getBackgroundImage(tags: List<String>): String {
        Log.d("NHAT", "Tags: $tags")
        var query = "inspiration"
        if (tags.isNotEmpty())
            query = tags.random()
        val response = pexelsApi.searchPhotos(
            query = query
        )
        if (!response.isSuccessful) {
            IOException("Failed to get background").printStackTrace()
            return ""
        }
        val photos = response.body()?.photos
        if (photos.isNullOrEmpty()) return ""
        return photos.first().src.large2x
    }

    override suspend fun searchQuote(keyword: String): List<Quote> {
        val response = quoteApi.searchQuotes(keyword)
        if (!response.isSuccessful) {
            IOException("Failed to get quote").printStackTrace()
            return listOf()
        }
        Log.d("NHAT", "searchQuote: " + response.body())
        return response.body()?.quotes?.map {
            it.toDomain()
        } ?: listOf()
    }

    override suspend fun getQuoteById(quoteId: String): Quote? {
        val response = quoteApi.getQuoteById(quoteId)
        if (!response.isSuccessful) {
            IOException("Failed to get quote").printStackTrace()
            return null
        }
        Log.d("NHAT", "searchQuote: " + response.body())
        return response.body().toDomain()
    }

    override suspend fun getQuotesByAuthor(authorId: String): List<Quote> {
        val response = quoteApi.getQuotesByAuthor(authorId)
        if (!response.isSuccessful) {
            IOException("Failed to get quote").printStackTrace()
            return listOf()
        }
        Log.d("NHAT", "searchQuote: " + response.body())
        return response.body()?.quotes?.map {
            it.toDomain()
        } ?: listOf()
    }

    override suspend fun getQuotesByCategory(categoryId: String): List<Quote> {
        val response = quoteApi.getQuotesByCategory(categoryId)
        if (!response.isSuccessful) {
            IOException("Failed to get quote").printStackTrace()
            return listOf()
        }
        Log.d("NHAT", "searchQuote: " + response.body())
        return response.body()?.quotes?.map {
            it.toDomain()
        } ?: listOf()
    }

    override suspend fun isQuoteSaved(quote: Quote): Boolean {
        // Kiểm tra xem quote đã được lưu trong bất kỳ collection nào
        return crossRefDao.getCollectionsForQuote(quote.id).isNotEmpty()
    }
}