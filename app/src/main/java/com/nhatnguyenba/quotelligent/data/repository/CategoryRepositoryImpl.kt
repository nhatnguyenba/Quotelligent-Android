package com.nhatnguyenba.quotelligent.data.repository

import com.nhatnguyenba.quotelligent.data.remote.api.FavQsQuoteApiService
import com.nhatnguyenba.quotelligent.data.remote.mapper.toDomain
import com.nhatnguyenba.quotelligent.domain.model.Category
import com.nhatnguyenba.quotelligent.domain.repository.CategoryRepository
import java.io.IOException
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val quoteApi: FavQsQuoteApiService,
) : CategoryRepository {

    override suspend fun searchCategories(keyword: String): List<Category> {
        val response = quoteApi.getFilters()
        if (!response.isSuccessful) {
            IOException("Failed to get quote").printStackTrace()
            return listOf()
        }
        return response.body()?.tags?.filter {
            it.name.contains(keyword, ignoreCase = true)
        }?.map {
            it.toDomain()
        } ?: listOf()
    }
}