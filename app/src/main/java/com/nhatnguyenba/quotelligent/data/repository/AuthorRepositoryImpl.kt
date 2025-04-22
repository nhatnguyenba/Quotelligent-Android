package com.nhatnguyenba.quotelligent.data.repository

import com.nhatnguyenba.quotelligent.data.remote.api.FavQsQuoteApiService
import com.nhatnguyenba.quotelligent.data.remote.mapper.toDomain
import com.nhatnguyenba.quotelligent.domain.model.Author
import com.nhatnguyenba.quotelligent.domain.repository.AuthorRepository
import java.io.IOException
import javax.inject.Inject

class AuthorRepositoryImpl @Inject constructor(
    private val quoteApi: FavQsQuoteApiService,
) : AuthorRepository {

    override suspend fun searchAuthors(keyword: String): List<Author> {
        val response = quoteApi.getFilters()
        if (!response.isSuccessful) {
            IOException("Failed to get quote").printStackTrace()
            return listOf()
        }
        return response.body()?.authors?.filter {
            it.name.contains(keyword, ignoreCase = true)
        }?.map {
            it.toDomain()
        } ?: listOf()
    }
}