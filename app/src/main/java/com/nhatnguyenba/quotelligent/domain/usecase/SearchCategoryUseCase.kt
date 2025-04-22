package com.nhatnguyenba.quotelligent.domain.usecase

import com.nhatnguyenba.quotelligent.domain.model.Category
import com.nhatnguyenba.quotelligent.domain.repository.CategoryRepository
import javax.inject.Inject

class SearchCategoryUseCase @Inject constructor(
    private val repository: CategoryRepository
) {
    suspend operator fun invoke(keyword: String): List<Category> =
        repository.searchCategories(keyword)
}