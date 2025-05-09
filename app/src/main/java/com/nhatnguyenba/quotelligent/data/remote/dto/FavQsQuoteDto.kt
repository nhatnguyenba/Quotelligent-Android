package com.nhatnguyenba.quotelligent.data.remote.dto

import com.google.gson.annotations.SerializedName

data class FavQsQuoteDto(
    @SerializedName("qotd_date")
    val qotdDate: String,
    val quote: QuoteDto,
)

data class SearchQuoteDto(
    val page: Long,
    @SerializedName("last_page")
    val lastPage: Boolean,
    val quotes: List<QuoteDto>,
)

data class QuoteDto(
    val id: Long,
    val dialogue: Boolean,
    val private: Boolean,
    val tags: List<String>,
    val url: String,
    @SerializedName("favorites_count")
    val favoritesCount: Long,
    @SerializedName("upvotes_count")
    val upvotesCount: Long,
    @SerializedName("downvotes_count")
    val downvotesCount: Long,
    val author: String,
    @SerializedName("author_permalink")
    val authorPermalink: String,
    @SerializedName("body")
    val content: String,
)
