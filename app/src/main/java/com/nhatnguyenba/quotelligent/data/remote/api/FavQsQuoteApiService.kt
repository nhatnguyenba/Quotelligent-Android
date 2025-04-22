package com.nhatnguyenba.quotelligent.data.remote.api

import com.nhatnguyenba.quotelligent.data.remote.dto.FavQsQuoteDto
import com.nhatnguyenba.quotelligent.data.remote.dto.FiltersResponse
import com.nhatnguyenba.quotelligent.data.remote.dto.SearchQuoteDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FavQsQuoteApiService {
    @GET("qotd")
    suspend fun getRandomQuote(): Response<FavQsQuoteDto>

    @GET("quotes")
    suspend fun searchQuotes(
        @Query("filter") filter: String,
    ): Response<SearchQuoteDto>

    @GET("typeahead")
    suspend fun getFilters(): Response<FiltersResponse>
}