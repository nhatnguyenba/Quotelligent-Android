package com.nhatnguyenba.quotelligent.domain.repository

import com.nhatnguyenba.quotelligent.domain.model.Category

interface CategoryRepository {
    suspend fun searchCategories(keyword: String): List<Category>
}