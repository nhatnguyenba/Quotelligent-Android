package com.nhatnguyenba.quotelligent.data.remote.api

import com.nhatnguyenba.quotelligent.data.remote.dto.FavQsQuoteDto
import retrofit2.Response
import retrofit2.http.GET

interface FavQsQuoteApiService {
    @GET("qotd")
    suspend fun getRandomQuote(): Response<FavQsQuoteDto>
}