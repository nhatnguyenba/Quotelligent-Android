package com.nhatnguyenba.quotelligent.data.remote.api

import com.nhatnguyenba.quotelligent.data.remote.dto.PexelsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PexelsApiService {
    @GET("search")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("per_page") perPage: Int = 1
    ): Response<PexelsResponse>
}