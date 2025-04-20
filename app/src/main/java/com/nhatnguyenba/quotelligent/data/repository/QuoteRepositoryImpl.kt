package com.nhatnguyenba.quotelligent.data.repository

import android.util.Log
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
) : QuoteRepository {

    override suspend fun getRandomQuote(): Quote {
        val response = quoteApi.getRandomQuote()
        if (!response.isSuccessful) {
            IOException("Failed to get quote").printStackTrace()
            return Quote()
        }
        Log.d("NHAT", "Body: " + response.body())
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
}